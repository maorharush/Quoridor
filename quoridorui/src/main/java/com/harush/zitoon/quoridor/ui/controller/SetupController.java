package com.harush.zitoon.quoridor.ui.controller;

import com.harush.zitoon.quoridor.core.dao.GameRecDAOImpl;
import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import com.harush.zitoon.quoridor.ui.view.components.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

    private Color[] defaultColours;
    private int playerIndex;
    private Board board = new Board(Settings.getSingleton().getBoardHeight(), Settings.getSingleton().getBoardWidth());
    private PawnType[] pawnTypes = PawnType.values();
    private GameSession gameSession = new GameSession(board, Settings.getSingleton().getRuleType(), new GameRecDAOImpl().getMaxID() + 1, new WinnerDeciderLogic());
    private int width = Settings.getSingleton().getBoardWidth();
    private int height = Settings.getSingleton().getBoardHeight();
    private List<AbstractPawnComponent> multiPlayerPawnComponents = new ArrayList<>();
    private VerticalWallComponent[][] multiPlayerVerticalWallComponents = makeVerticalWallComponents();
    private HorizontalWallComponent[][] multiPlayerHorizontalWallComponents = makeHorizontalWallComponents();
    private int[] xStartingPositions = getStartingPositionsX(width);
    private int[] yStartingPositions = getStartingPositionsY(height, width);

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
        setupGame(multiPlayerTable.getItems(), multiPlayerPawnComponents, multiPlayerVerticalWallComponents, multiPlayerHorizontalWallComponents);
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

        List<Player> players = new ArrayList<>(2);
        List<Pawn> pawns = makePawns(2);

        HumanPawnComponent humanPawnComponent = new HumanPawnComponent(xStartingPositions[0], yStartingPositions[0], humanPlayerName, pawns.get(0));
        AIPawnComponent aiPawnComponent = new AIPawnComponent(xStartingPositions[1], yStartingPositions[1], aiPlayerName, pawns.get(1));

        VerticalWallComponent[][] verticalWallComponents = makeVerticalWallComponents();
        HorizontalWallComponent[][] horizontalWallComponents = makeHorizontalWallComponents();

        Player player1 = new HumanPlayer(humanPlayerName, pawns.get(0));
//        Player player2 = new DumbAIPlayer(aiPlayerName, aiPawnComponent, verticalWallComponents, horizontalWallComponents);
        Player player2 = new WantsToWinAIPlayer(aiPlayerName, aiPawnComponent, verticalWallComponents, horizontalWallComponents);
        players.add(player1);
        players.add(player2);

        List<AbstractPawnComponent> pawnComponents = new ArrayList<>();
        pawnComponents.add(humanPawnComponent);
        pawnComponents.add(aiPawnComponent);

        setupGame(players, pawnComponents, verticalWallComponents, horizontalWallComponents);
    }

    @FXML
    private void on2PlayerBtn(ActionEvent action) {
        int numPlayers = 2;

        List<String> playerNames = readPlayersNamesFromUser(numPlayers);
        List<Pawn> pawns = makePawns(numPlayers);
        List<AbstractPawnComponent> pawnComponents = makePawnComponents(playerNames, pawns);
        List<Player> players = makePlayers(numPlayers, playerNames, pawns);
        VerticalWallComponent[][] verticalWallComponents = makeVerticalWallComponents();
        HorizontalWallComponent[][] horizontalWallComponents = makeHorizontalWallComponents();

        setupGame(players, pawnComponents, verticalWallComponents, horizontalWallComponents);
    }

    @FXML
    private void on4PlayerBtn(ActionEvent action) {
        int numPlayers = 4;

        List<String> playerNames = readPlayersNamesFromUser(numPlayers);
        List<Pawn> pawns = makePawns(numPlayers);
        List<AbstractPawnComponent> pawnComponents = makePawnComponents(playerNames, pawns);
        List<Player> players = makePlayers(numPlayers, playerNames, pawns);
        VerticalWallComponent[][] verticalWallComponents = makeVerticalWallComponents();
        HorizontalWallComponent[][] horizontalWallComponents = makeHorizontalWallComponents();

        setupGame(players, pawnComponents, verticalWallComponents, horizontalWallComponents);
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
    private void setupGame(List<Player> players, List<AbstractPawnComponent> pawnComponents, VerticalWallComponent[][] verticalWallComponents, HorizontalWallComponent[][] horizontalWallComponents) {
        setupGameSession(players);
        Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
        centerStage(stage, width, height);
        new MainGame(stage, gameSession, pawnComponents, verticalWallComponents, horizontalWallComponents);
        stage.show();
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

    private List<Player> makePlayers(int numPlayers, List<String> playerNames, List<Pawn> pawns) {
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
}
