package com.harush.zitoon.quoridor.ui.controller;

import com.harush.zitoon.quoridor.core.dao.*;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.Player2PlayerDBOConverter;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.Player2PlayerDBOConverterImpl;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.PlayerAction2GameRecDBOConverterImpl;
import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import com.harush.zitoon.quoridor.ui.view.components.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Handles setup actions
 */
public class SetupController extends AbstractController implements Initializable {

    @FXML
    private TableView<Player> multiPlayerTable;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> pawnColumn;
    @FXML
    private AnchorPane multiPlayerPane;
    @FXML
    private Button addPlayerBtn;
    @FXML
    private Button playBtn;
    private int width = Settings.getSingleton().getBoardWidth();
    private int height = Settings.getSingleton().getBoardHeight();
    private Color[] defaultColours;
    private int playerIndex;
    private Board board = new Board(Settings.getSingleton().getBoardHeight(), Settings.getSingleton().getBoardWidth());
    private PawnType[] pawnTypes = PawnType.values();
    private DAOFactory daoFactory = DAOFactoryImpl.instance();
    private GameDAO gameDAO = DAOFactoryImpl.instance().getDAO(GameDAO.TABLE_NAME);
    private GameSession gameSession = new GameSession(gameDAO.getLastGameID() + 1, board, Settings.getSingleton().getRuleType(), daoFactory, new WinnerDeciderLogic(), new Player2PlayerDBOConverterImpl());
    private VerticalWallComponent[][] verticalWallComponents = makeVerticalWallComponents();
    private HorizontalWallComponent[][] horizontalWallComponents = makeHorizontalWallComponents();
    private List<AbstractPawnComponent> multiPlayerPawnComponents = new ArrayList<>();
    private VerticalWallComponent[][] multiPlayerVerticalWallComponents = makeVerticalWallComponents();
    private HorizontalWallComponent[][] multiPlayerHorizontalWallComponents = makeHorizontalWallComponents();
    private int[] xStartingPositions = getStartingPositionsX(width);
    private int[] yStartingPositions = getStartingPositionsY(height, width);
    private GamePersistenceService gamePersistenceService;
    private PopulateBoardUtil populateBoardUtil = new PopulateBoardUtilImpl();

    public SetupController() {
        gamePersistenceService = getGamePersistenceService();
        gameSession.setGamePersistenceService(gamePersistenceService);
    }

    /**
     * Action triggered by pressing the back button.
     *
     * @param event the action
     */
    @FXML
    private void onBackBtn(ActionEvent event) {
        Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
        loadScreen(stage, "mainmenu.fxml");
    }

    /**
     * Action triggered by pressing the play button.
     *
     * @param event the action
     */
    @FXML
    private void onPlayBtn(ActionEvent event) {
        setupNewGame(multiPlayerTable.getItems(), multiPlayerVerticalWallComponents, multiPlayerHorizontalWallComponents);
    }

    /**
     * Action triggered by pressing the add player button. Adapted from <a href="http://code.makery.ch/blog/javafx-dialogs-official/">Makery</a>.
     *
     * @param event the action
     */
    @FXML
    private void onAddPlayerBtn(ActionEvent event) {
        Dialog<Pair<String, String>> popup = new Dialog<>();
        popup.setTitle("Add a Player");
        popup.setHeaderText("Add a player to this game session");

        ButtonType buttonType = new ButtonType("Add Player", ButtonData.OK_DONE);
        popup.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        ColorPicker colorPicker = new ColorPicker(defaultColours[playerIndex]);
        colorPicker.setPromptText("Pawn colour");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Pawn colour:"), 0, 1);
        grid.add(colorPicker, 1, 1);

        Node addButton = popup.getDialogPane().lookupButton(buttonType);
        addButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(newValue.trim().isEmpty()));

        popup.getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);
        popup.showAndWait();
        String hexColor = convertColour(colorPicker);
        System.out.println(colorPicker.getValue().toString());
        String playerName = name.getText();

        if (!playerName.isEmpty() && !hexColor.isEmpty()) {
            Pawn pawn = makePawn(PawnType.getByHexColor(hexColor));
            HumanPawnComponent humanPawnComponent = makeHumanPawnComponent(xStartingPositions[playerIndex], yStartingPositions[playerIndex], playerName, pawn);
            multiPlayerPawnComponents.add(humanPawnComponent);

            Player player = new HumanPlayer(playerName, pawn);
            System.out.println(hexColor);
            multiPlayerTable.getItems().add(player);
            playerIndex++;
        }
        if (multiPlayerTable.getItems().size() == GameSession.MAX_PLAYERS) {
            addPlayerBtn.setDisable(true);
        }

        if ((multiPlayerTable.getItems().size() % 2 == 0) && (multiPlayerTable.getItems().size() != 0)) {
            playBtn.setDisable(false);
        } else {
            playBtn.setDisable(true);
        }
    }

    @FXML
    private void on1PlayerBtn(ActionEvent actionEvent) {
        TextInputDialog textInputDialog = getInsertPlayerNameTextInputDialog(1);

        Optional<String> optionalPlayerName = textInputDialog.showAndWait();
        String humanPlayerName = optionalPlayerName.orElse("Player");
        String aiPlayerName = "Wall-e";

        List<Player> players = getVsAIPlayers(humanPlayerName, aiPlayerName);
        setupNewGame(players);
    }

    private List<Player> getVsAIPlayers(String humanPlayerName, String aiPlayerName) {
        List<Player> players = new ArrayList<>(2);
        List<Pawn> pawns = makePawns(2);

        HumanPawnComponent humanPawnComponent = new HumanPawnComponent(xStartingPositions[0], yStartingPositions[0], humanPlayerName, pawns.get(0));
        AIPawnComponent aiPawnComponent = new AIPawnComponent(xStartingPositions[1], yStartingPositions[1], aiPlayerName, pawns.get(1));

        Player player1 = new HumanPlayer(humanPlayerName, humanPawnComponent);
//        Player player2 = new DumbAIPlayer(aiPlayerName, aiPawnComponent, verticalWallComponents, horizontalWallComponents);
        Player player2 = new WantsToWinAIPlayer(aiPlayerName, aiPawnComponent, verticalWallComponents, horizontalWallComponents);
        players.add(player1);
        players.add(player2);
        return players;
    }

    @FXML
    private void on2PlayerBtn(ActionEvent action) {
        List<Player> players = getPlayers(2);
        setupNewGame(players);
    }

    @FXML
    private void on4PlayerBtn(ActionEvent action) {
        List<Player> players = getPlayers(4);
        setupNewGame(players);
    }

    private List<Player> getPlayers(int numPlayers) {
        List<String> playerNames = readPlayersNamesFromUser(numPlayers);
        List<Pawn> pawns = makePawns(numPlayers);
        List<AbstractPawnComponent> pawnComponents = makePawnComponents(playerNames, pawns);
        return makePlayers(numPlayers, playerNames, pawnComponents);
    }

    private VerticalWallComponent[][] makeVerticalWallComponents() {
        final VerticalWallComponent[][] verticalWalls = new VerticalWallComponent[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                VerticalWallComponent wall = new VerticalWallComponent(x, y, verticalWalls, gameSession, new VerticalWallLogic(x, y, gameSession));
                verticalWalls[x][y] = wall;
            }
        }

        return verticalWalls;
    }

    private HorizontalWallComponent[][] makeHorizontalWallComponents() {
        final HorizontalWallComponent[][] horizontalWalls = new HorizontalWallComponent[width][height];
        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                HorizontalWallComponent wall = new HorizontalWallComponent(x, y, horizontalWalls, gameSession, new HorizontalWallLogic(x, y, gameSession));
                horizontalWalls[x][y] = wall;
            }
        }

        return horizontalWalls;
    }

    /**
     * Sets up a game with {@link Player players}
     *
     * @param players the players
     */
    private void setupNewGame(List<Player> players, VerticalWallComponent[][] verticalWallComponents, HorizontalWallComponent[][] horizontalWallComponents) {
        setupNewGameWithWallComponents(players, verticalWallComponents, horizontalWallComponents);
    }

    private void setupNewGameWithWallComponents(List<Player> players, VerticalWallComponent[][] verticalWallComponents, HorizontalWallComponent[][] horizontalWallComponents) {
        gamePersistenceService.initGamePersistence(players, gameSession);
        setupGameSession(players);
        Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
        centerStage(stage, width, height);
        List<AbstractPawnComponent> pawnComponents = players.stream().map(player -> (AbstractPawnComponent) player.getPawn()).collect(Collectors.toList());
        new MainGame(stage, gameSession, pawnComponents, verticalWallComponents, horizontalWallComponents);
        stage.show();
    }

    /**
     * Sets up a game with {@link Player players}
     *
     * @param players the players
     */
    private void setupNewGame(List<Player> players) {
        setupNewGameWithWallComponents(players, verticalWallComponents, horizontalWallComponents);
    }

    /**
     * Converts a {@link ColorPicker} value to hex.
     *
     * @param colourPicker the {@link ColorPicker}
     * @return the converted colour
     */
    private String convertColour(ColorPicker colourPicker) {
        int green = (int) (colourPicker.getValue().getGreen() * 255);
        int blue = (int) (colourPicker.getValue().getBlue() * 255);
        int red = (int) (colourPicker.getValue().getRed() * 255);
        return "#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        int lastGameID = gameDAO.getLastGameID();
        if (lastGameID != -1) {
            boolean shouldLoadGame = askUserIfShouldLoadGame();
            if (shouldLoadGame) {
                loadGame();
            }
        }

        doStuffWeWillProbablyDelete();
    }

    private void loadGame() {
        SavedGame savedGame = gamePersistenceService.loadGame();
        gameSession.setGameID(savedGame.getGameID());
        List<PlayerHistory> playerHistories = savedGame.getPlayerHistories();
        PopulateBoardUtil populateBoardUtil = new PopulateBoardUtilImpl();
        populateBoardUtil.populateBoard(board, playerHistories);

        if (savedGame.isVsAI()) {
            loadVsAIGame(savedGame);
        } else {
            loadVsHumansGame(savedGame);
        }
    }

    private void loadVsHumansGame(SavedGame savedGame) {
        List<PlayerHistory> playerHistories = savedGame.getPlayerHistories();
        int numPlayers = playerHistories.size();
        populateBoardUtil.populateBoard(board, playerHistories);

        List<Pawn> pawns = makePawns(numPlayers);
        List<String> playerNames = playerHistories.stream().map(PlayerHistory::getPlayerName).collect(Collectors.toList());
        List<AbstractPawnComponent> pawnComponents = makePawnComponents(playerNames, pawns);
        List<Player> loadedPlayers = makePlayers(numPlayers, playerNames, pawnComponents);

        populatePlayersByHistory(loadedPlayers, playerHistories);
        setupSavedGame(loadedPlayers);
    }

    private void loadVsAIGame(SavedGame savedGame) {
        List<PlayerHistory> playerHistories = savedGame.getPlayerHistories();
        populateBoardUtil.populateBoard(board, playerHistories);

        String humanPlayerName;
        String aiPlayerName;
        if (playerHistories.get(0).isAI()) {
            aiPlayerName = playerHistories.get(0).getPlayerName();
            humanPlayerName = playerHistories.get(1).getPlayerName();
        } else {
            humanPlayerName = playerHistories.get(0).getPlayerName();
            aiPlayerName = playerHistories.get(1).getPlayerName();
        }

        List<Player> players = getVsAIPlayers(humanPlayerName, aiPlayerName);

        populatePlayersByHistory(players, playerHistories);
        setupSavedGame(players);
    }

    private void populatePlayersByHistory(List<Player> players, List<PlayerHistory> playerHistories) {
        for (Player player : players) {
            PlayerHistory playerHistory = playerHistories.stream().filter(ph -> ph.getPlayerName().equals(player.getName())).findFirst().get();
            populatePlayerByHistory(player, playerHistory);
        }
    }

    private void populatePlayerByHistory(Player player, PlayerHistory playerHistory) {
        Statistics statistics = new Statistics();
        statistics.setNumOfTotalMoves(playerHistory.getNumTotalMoves());
        statistics.setNumOfWallsUsed(playerHistory.getWallPlacements().size());
        player.setStats(statistics);
        player.setNumWalls(playerHistory.getNumWallsLeft());
        player.getPawn().setCurrentCoordinate(playerHistory.getCurrentPawnCoordinate());
        player.getPawn().setInitialCoordinate(playerHistory.getInitialPawnCoordinate());
        player.setAI(playerHistory.isAI());
    }

    private void setupSavedGame(List<Player> players) {
        setupGameSession(players);
        Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
        centerStage(stage, width, height);
        List<AbstractPawnComponent> pawnComponents = players.stream().map(player -> (AbstractPawnComponent) player.getPawn()).collect(Collectors.toList());
        new MainGame(stage, gameSession, pawnComponents, verticalWallComponents, horizontalWallComponents);
        stage.show();
    }

    private boolean askUserIfShouldLoadGame() {
        boolean shouldLoadGame = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Resume previous game");
        alert.setHeaderText("Would you like to resume the previous game?");
        alert.setContentText("A previous game can be resumed.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            shouldLoadGame = result.get() == buttonTypeYes;
        }
        return shouldLoadGame;
    }

    private void doStuffWeWillProbablyDelete() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pawnColumn.setCellValueFactory(new PropertyValueFactory<>("pawnColour"));
        pawnColumn.setCellFactory(column -> new TableCell<Player, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setStyle("-fx-background-color:" + getString() + ";");
            }

            private String getString() {
                return getItem() == null ? "" : getItem();
            }
        });
        multiPlayerTable.setPlaceholder(new Label("No players yet"));
        defaultColours = new Color[]{Color.valueOf("#663366"), Color.valueOf("#b3e6b3"), Color.valueOf("#334db3"), Color.valueOf("#ff6666")};
        playerIndex = 0;
    }

    private void setupGameSession(List<Player> players) {
        for (Player player : players) {
            gameSession.addPlayer(player);
        }
    }

    private List<String> readPlayersNamesFromUser(int numPlayers) {
        List<String> playerNames = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            Optional<String> optionalPlayerName = getInsertPlayerNameTextInputDialog(i + 1).showAndWait();
            String playerName = optionalPlayerName.orElse("Player" + (i + 1));
            playerNames.add(playerName);
        }
        return playerNames;
    }

    private TextInputDialog getInsertPlayerNameTextInputDialog(int playerNumber) {
        TextInputDialog textInputDialog = new TextInputDialog("Player");
        textInputDialog.setTitle("Insert player name");
        textInputDialog.setHeaderText(String.format("Player %d's Name:", playerNumber));
        textInputDialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
        centerStage(stage, height, width);
        return textInputDialog;
    }

    private List<Player> makePlayers(int numPlayers, List<String> playerNames, List<AbstractPawnComponent> pawns) {
        List<Player> players = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            HumanPlayer humanPlayer = makeHumanPlayer(playerNames.get(i), pawns.get(i));
            players.add(humanPlayer);
        }
        return players;
    }

    /**
     * Sets up all the pawns in the game.
     */
    private List<AbstractPawnComponent> makePawnComponents(List<String> playerNames, List<Pawn> pawns) {
        List<AbstractPawnComponent> pawnComponentList = new ArrayList<>(GameSession.MAX_PLAYERS);

        for (int i = 0; i < playerNames.size(); i++) {
            HumanPawnComponent pawn = makeHumanPawnComponent(xStartingPositions[i], yStartingPositions[i], playerNames.get(i), pawns.get(i));
            pawnComponentList.add(pawn);
        }

        return pawnComponentList;
    }

    private int[] getStartingPositionsY(int height, int width) {
        int[] yStartingPositions = null;
        if (this.gameSession.getRuleType() == RuleType.CHALLENGE) {
            yStartingPositions = new int[]{(height - 1), (0), (width - 1), 0};
        } else if (this.gameSession.getRuleType() == RuleType.STANDARD) {
            yStartingPositions = new int[]{(height - 1), (0), (height / 2), (height / 2)};
        }
        return yStartingPositions;
    }

    private int[] getStartingPositionsX(int width) {
        int xStartingPositions[] = null;
        if (this.gameSession.getRuleType() == RuleType.CHALLENGE) {
            xStartingPositions = new int[]{(0), (width - 1), (width - 1), 0};
        } else if (this.gameSession.getRuleType() == RuleType.STANDARD) {
            xStartingPositions = new int[]{(width / 2), (width / 2), (0), (width - 1)};
        }
        return xStartingPositions;
    }

    /**
     * Creates a new {@link HumanPawnComponent}.
     *
     * @param x the starting x coordinate
     * @param y the starting y coordinate
     * @return the pawn component
     */
    private HumanPawnComponent makeHumanPawnComponent(int x, int y, String playerName, Pawn pawn) {
        return new HumanPawnComponent(x, y, playerName, pawn);
    }

    private List<Pawn> makePawns(int numPawns) {
        List<Pawn> pawns = new ArrayList<>(numPawns);
        for (int i = 0; i < numPawns; i++) {
            pawns.add(makePawn(pawnTypes[i]));
        }
        return pawns;
    }

    private Pawn makePawn(PawnType pawnType) {
        return new PawnLogic(gameSession, pawnType);
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    private HumanPlayer makeHumanPlayer(String playerName, Pawn pawn) {
        return new HumanPlayer(playerName, pawn);
    }

    private GamePersistenceServiceImpl getGamePersistenceService() {
        return new GamePersistenceServiceImpl(DAOFactoryImpl.instance(), getPlayersHistoryFactory(), getPlayerAction2GameRecDBOConverter(), getPlayer2PlayerDBOConverter());
    }

    private Player2PlayerDBOConverter getPlayer2PlayerDBOConverter() {
        return new Player2PlayerDBOConverterImpl();
    }

    private PlayerAction2GameRecDBOConverterImpl getPlayerAction2GameRecDBOConverter() {
        return new PlayerAction2GameRecDBOConverterImpl();
    }

    private PlayersHistoryFactoryImpl getPlayersHistoryFactory() {
        return new PlayersHistoryFactoryImpl(daoFactory.getDAO(GameRecDAO.TABLE_NAME), daoFactory.getDAO(PlayerDAO.TABLE_NAME));
    }
}
