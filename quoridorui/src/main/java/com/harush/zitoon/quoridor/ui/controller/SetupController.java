package com.harush.zitoon.quoridor.ui.controller;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import com.harush.zitoon.quoridor.ui.view.components.AIPawnComponent;
import com.harush.zitoon.quoridor.ui.view.components.AbstractPawnComponent;
import com.harush.zitoon.quoridor.ui.view.components.HumanPawnComponent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private int colourIndex;
    private Board board = new Board(Settings.getSingleton().getBoardHeight(), Settings.getSingleton().getBoardWidth());
    private PawnType[] pawnTypes = PawnType.values();
    private GameSession gameSession = new GameSession(board, Settings.getSingleton().getRuleType());
    private String[] colors = {"#663366", "#b3e6b3", "#334db3", "#ff6666"};
    private String[] playerNames = {"1", "2", "3", "4"};

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
//        setupGame(multiPlayerTable.getItems());
    }

    /**
     * Action triggered by pressing the add player button.
     * Adapted from <a href="http://code.makery.ch/blog/javafx-dialogs-official/">Makery</a>.
     *
     * @param event the action
     */
    @FXML
    private void onAddPlayerBtn(ActionEvent event) {
        Dialog<Pair<String, String>> popup = new Dialog<>();
        popup.setTitle("Add a Player");
        popup.setHeaderText("Add a player to this game session");

        ButtonType loginButtonType = new ButtonType("Add Player", ButtonData.OK_DONE);
        popup.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Name");
        ColorPicker colorPicker = new ColorPicker(defaultColours[colourIndex]);
        colorPicker.setPromptText("Pawn colour");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Pawn colour:"), 0, 1);
        grid.add(colorPicker, 1, 1);

        Node addButton = popup.getDialogPane().lookupButton(loginButtonType);
        addButton.setDisable(true);

        name.textProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(newValue.trim().isEmpty()));

        popup.getDialogPane().setContent(grid);

        Platform.runLater(name::requestFocus);
        popup.showAndWait();
        String hexColor = convertColour(colorPicker);
        System.out.println(colorPicker.getValue().toString());
        if (!name.getText().isEmpty() && !hexColor.isEmpty()) {
            Player player = new HumanPlayer(name.getText(), new PawnLogic(gameSession, pawnTypes[colourIndex]), hexColor);
            System.out.println(hexColor);
            multiPlayerTable.getItems().add(player);
            colourIndex++;
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
        List<String> colors = getColors(2);
        List<String> playerNames = getPlayerNames(2);
        List<Player> players = new ArrayList<>(2);
        List<Pawn> pawns = makePawns(2);

        int height = Settings.getSingleton().getBoardHeight();
        int width = Settings.getSingleton().getBoardWidth();

        int[] xStartingPositions = getStartingPositionsX(width);
        int[] yStartingPositions = getStartingPositionsY(height, width);

        HumanPawnComponent humanPawnComponent = new HumanPawnComponent(xStartingPositions[0], yStartingPositions[0], colors.get(0), playerNames.get(0), pawns.get(0));
        AIPawnComponent aiPawnComponent = new AIPawnComponent(xStartingPositions[1], yStartingPositions[1], colors.get(1), playerNames.get(1), pawns.get(1));

        Player player1 = new HumanPlayer(playerNames.get(0), humanPawnComponent, colors.get(0));
        Player player2 = new DumbAIPlayer(playerNames.get(1), aiPawnComponent, colors.get(1));
        players.add(player1);
        players.add(player2);

        List<AbstractPawnComponent> pawnComponents = new ArrayList<>();
        pawnComponents.add(humanPawnComponent);
        pawnComponents.add(aiPawnComponent);

        setupGame(players, pawnComponents);
    }

    @FXML
    private void on2PlayerBtn(ActionEvent action) {
//        List<Player> players = new ArrayList<>(2);
//        Player player1 = new HumanPlayer("1", new PawnLogic(gameSession, pawnTypes[0]), "#663366");
//        Player player2 = new HumanPlayer("2", new PawnLogic(gameSession, pawnTypes[1]), "#b3e6b3");
//        players.add(player1);
//        players.add(player2);
//        setupGame(players);
    }

    @FXML
    private void on4PlayerBtn(ActionEvent action) {
//        List<Player> players = new ArrayList<>(2);
//        Player player1 = new HumanPlayer("1", new PawnLogic(gameSession, pawnTypes[0]), "#663366");
//        Player player2 = new HumanPlayer("2", new PawnLogic(gameSession, pawnTypes[1]), "#b3e6b3");
//        Player player3 = new HumanPlayer("3", new PawnLogic(gameSession, pawnTypes[2]), "#334db3");
//        Player player4 = new HumanPlayer("4", new PawnLogic(gameSession, pawnTypes[3]), "#ff6666");
//        players.add(player1);
//        players.add(player2);
//        players.add(player3);
//        players.add(player4);
//        setupGame(players);
    }

    /**
     * Sets up a game with {@link Player players}
     *
     * @param players the players
     */
    private void setupGame(List<Player> players, List<AbstractPawnComponent> pawnComponents) {
        setupGameSession(players);
        Stage stage = (Stage) multiPlayerPane.getScene().getWindow();
        new MainGame(stage, gameSession, pawnComponents);
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
        return "#" + Integer.toHexString(red)
                + Integer.toHexString(green)
                + Integer.toHexString(blue);
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
        colourIndex = 0;

    }

    private void setupGameSession(List<Player> players) {
        for (Player player : players) {
            gameSession.addPlayer(player);
        }
    }

    /**
     * Sets up all the pawns in the game.
     */
    private List<AbstractPawnComponent> makePawnComponents(List<String> colors, List<String> playerNames, List<Pawn> pawns) {
        List<AbstractPawnComponent> pawnComponentList = new ArrayList<>(GameSession.MAX_PLAYERS);
        int height = Settings.getSingleton().getBoardHeight();
        int width = Settings.getSingleton().getBoardWidth();
        //Starting positions for player 1, player 2, player 3, player 4 based on game type

        int[] xStartingPositions = getStartingPositionsX(width);
        int[] yStartingPositions = getStartingPositionsY(height, width);

        for (int i = 0; i < playerNames.size(); i++) {
            //Loop through hardcoded starting positions and pawn types to assign to each player's pawn
            AbstractPawnComponent pawn = makePawnComponent(xStartingPositions[i], yStartingPositions[i], colors.get(i), playerNames.get(i), pawns.get(i));
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
     * Creates a new {@link AbstractPawnComponent}.
     *
     * @param x    the starting x coordinate
     * @param y    the starting y coordinate
     * @return the pawn component
     */
    private AbstractPawnComponent makePawnComponent(int x, int y, String color, String playerName, Pawn pawn) {
//        return new AbstractPawnComponent(x, y, color, playerName, pawn);
        return null;
    }

    private List<String> getColors(int numColors) {
        List<String> colors = new ArrayList<>(numColors);

        for (int i = 0; i < numColors; i++) {
            colors.add(this.colors[i]);
        }
        return colors;
    }

    private List<String> getPlayerNames(int numPlayers) {
        List<String> playerNames = new ArrayList<>(numPlayers);

        for (int i = 0; i < numPlayers; i++) {
            playerNames.add(this.playerNames[i]);
        }
        return playerNames;
    }

    private List<Pawn> makePawns(int numPawns) {
        List<Pawn> pawns = new ArrayList<>(numPawns);
        for (int i = 0; i < numPawns; i++) {
            pawns.add(new PawnLogic(gameSession, pawnTypes[i]));
        }
        return pawns;
    }
}
