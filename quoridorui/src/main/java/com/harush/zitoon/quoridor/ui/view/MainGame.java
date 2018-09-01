package com.harush.zitoon.quoridor.ui.view;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.controller.StatsController;
import com.harush.zitoon.quoridor.ui.view.components.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainGame extends Application implements GameScreen, MainScreen, Observer {
    public static final int TILE_SIZE = Settings.getSingleton().getTileSize();

    private final VerticalWallComponent[][] verticalWalls;
    private final List<AbstractPawnComponent> pawnComponentList;
    private GameSession gameSession;
    private int height;
    private int width;
    private TileComponent[][] tileBoard;
    private HorizontalWallComponent[][] horizontalWalls;
    private Group tileGroup = new Group();
    private Group pawnGroup = new Group();
    private Group horizontalWallGroup = new Group();
    private Group verticalWallGroup = new Group();
    private Label currentTurnLabel;
    private Label wallsLabel;

    public MainGame(Stage stage, GameSession gameSession, List<AbstractPawnComponent> pawnComponentList, VerticalWallComponent[][] verticalWalls) {
        setupModel(gameSession.getBoard());

        this.verticalWalls = verticalWalls;
        this.currentTurnLabel = new Label();
        this.wallsLabel = new Label();
        this.gameSession = gameSession;
        this.pawnComponentList = pawnComponentList;

        gameSession.addObserver(this);

        showStage(stage, "resources/icons/favicon.png");
        startGame();
    }

    @Override
    public void start(Stage primaryStage) {
        showStage(primaryStage, "res/icons/favicon.png");
    }

    /**
     * Sets up the model.
     * @param board   the board
     */
    private void setupModel(Board board) {
        Settings settings = Settings.getSingleton();

        height = settings.getBoardHeight();
        width = settings.getBoardWidth();
        tileBoard = new TileComponent[board.getWidth()][board.getHeight()];
        horizontalWalls = new HorizontalWallComponent[board.getWidth()][board.getHeight()];
    }

    private void startGame() {
        gameSession.startGame();
    }

    private void showStage(Stage stage, String s) {
        Scene scene = new Scene(createContent());
        stage.getIcons().add(new Image(s));
        stage.setTitle("Quoridor");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates an information panel on the side of the board.
     *
     * @return the information panel
     */
    private Pane createInfoPanel() {
        Pane panel = new Pane();
        Button button = new Button("Main Menu");
        button.setOnAction((e) -> {
            Stage stage = (Stage) button.getScene().getWindow();
            loadMainMenu(stage);
        });
        int offset = Settings.getSingleton().getBoardWidth();
        button.setTranslateY(150);
        currentTurnLabel.setText(gameSession.getCurrentPlayer().getName() + "'s turn");
        currentTurnLabel.setTextFill(Color.valueOf(gameSession.getCurrentPlayer().getPawnColour()));
        currentTurnLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        wallsLabel.setText("Walls left: " + gameSession.getCurrentPlayer().getWalls());
        wallsLabel.setTextFill(Color.valueOf(gameSession.getCurrentPlayer().getPawnColour()));
        wallsLabel.setTranslateY(50);
        panel.getChildren().addAll(currentTurnLabel, wallsLabel, button);
        if (offset == 7) {
            panel.setTranslateX(350);
        } else if (offset == 11) {
            panel.setTranslateX(550);
        } else {
            panel.setTranslateX(450);
        }
        return panel;
    }

    /**
     * Creates the main board content.
     *
     * @return the content
     */
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize((width * TILE_SIZE) + 85, height * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pawnGroup, horizontalWallGroup, verticalWallGroup, createInfoPanel());

        //Add tiles to the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TileComponent tile = new TileComponent(x, y);
                tileBoard[x][y] = tile;
                tileGroup.getChildren().add(tile);
            }
        }
        //Add vertical walls to the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                verticalWallGroup.getChildren().add(verticalWalls[x][y]);
            }
        }

        //Add horizontal walls to the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (y == 0) {
                    continue;
                }
                HorizontalWallComponent wall = new HorizontalWallComponent(x, y);
                horizontalWalls[x][y] = wall;
                final int thisX = x;
                final int thisY = y;
                int nextWallX = x + 1;
                int nextWallY = y;

                wall.setOnMouseEntered(e -> {
                    if (nextWallX > 0 && nextWallX < width) {
                        if (!gameSession.getBoard().containsWall(thisX, thisY, true) && !gameSession.getBoard().containsWall(nextWallX, nextWallY, true)) {
                            wall.setFill(Color.valueOf("bbbbbb"));
                            horizontalWalls[nextWallX][nextWallY].setFill(Color.valueOf("bbbbbb"));
                        }
                    }

                });
                wall.setOnMouseExited(e -> {

                    if (nextWallX > 0 && nextWallX < width) {
                        if (!gameSession.getBoard().containsWall(thisX, thisY, true) && !gameSession.getBoard().containsWall(nextWallX, nextWallY, true)) {
                            wall.setFill(Color.rgb(153, 217, 234, 0.8));
                            horizontalWalls[nextWallX][nextWallY].setFill(Color.rgb(153, 217, 234, 0.8));
                        }
                    }
                });
                wall.setOnMousePressed(e -> {
                    if (nextWallX > width) { //A horizontal wall cannot be placed at the very top of the board
                        return;
                    }

                    if (thisX == width) { //A horizontal wall cannot be placed at the very edge of the board
                        System.out.println("You cannot place a wall here.");
                        return;
                    }

                    if (e.isPrimaryButtonDown()) {
                        if (gameSession.getBoard().containsWall(thisX, thisY, true) ||
                                gameSession.getBoard().containsWall(nextWallX, nextWallY, true)) {
                            System.out.println("You cannot place a wall here.");
                            return;
                        }
                        if (gameSession.getCurrentPlayer().getWalls() == 0) {
                            System.out.println("You do not have any walls left.");
                            return;
                        }
                        gameSession.getBoard().setWall(thisX, thisY, true, true, gameSession.getCurrentPlayer());
                        wall.setFill(Color.valueOf(gameSession.getCurrentPlayer().getPawnColour()));
                        System.out.println("1. Wall placed at X: " + thisX + " Y: " + thisY);
                        if (nextWallX > 0 && nextWallX < width) {
                            gameSession.getBoard().setWall(nextWallX, nextWallY, true, false, gameSession.getCurrentPlayer());
                            horizontalWalls[nextWallX][nextWallY].setFill(Color.valueOf(gameSession.getCurrentPlayer().getPawnColour()));
                            System.out.println("2. Wall placed at: X" + nextWallX + " " + nextWallY);
                        }
                        gameSession.getCurrentPlayer().getStatistics().incrementWallsUsed();
                        gameSession.getCurrentPlayer().decrementWalls();
                        gameSession.updateTurn();
                    } else if (e.isSecondaryButtonDown()) {
                        if (gameSession.getRuleType() == RuleType.CHALLENGE) {
                            if (!gameSession.getBoard().containsWall(thisX, thisY, true)) {
                                System.out.println("No wall here");
                                return;
                            }
                            if (gameSession.getBoard().getWall(thisX, thisY, true).getPlacedBy() == gameSession.getCurrentPlayer()) {
                                System.out.println("You cannot remove your own walls.");
                                return;
                            }
                            if (gameSession.getBoard().getWall(thisX, thisY, true).getIsFirst()) {
                                if (nextWallX >= 0 && nextWallX <= width) {
                                    horizontalWalls[nextWallX][nextWallY].setFill(Color.rgb(153, 217, 234, 0.8));
                                    gameSession.getBoard().removeWall(nextWallX, nextWallY, true);
                                }
                            } else {
                                int previousWallX = nextWallX - 2;
                                if (previousWallX >= 0 && nextWallX <= width) {
                                    horizontalWalls[previousWallX][nextWallY].setFill(Color.rgb(153, 217, 234, 0.8));
                                    gameSession.getBoard().removeWall(previousWallX, nextWallY, true);
                                }
                            }
                            gameSession.getBoard().getWall(thisX, thisY, true).getPlacedBy().incrementWalls();
                            //gameSession.getBoard().getWall(thisX, thisY, true).getPlacedBy().getStatistics().decrementWallsUsed();
                            gameSession.getBoard().removeWall(thisX, thisY, true);
                            wall.setFill(Color.rgb(153, 217, 234, 0.8));
                            gameSession.updateTurn();
                        } else {
                            System.out.println("You can only remove walls in a game with " + RuleType.CHALLENGE + " rules.");
                        }
                    }

                });
                horizontalWallGroup.getChildren().add(wall);
            }
        }
        pawnGroup.getChildren().addAll(pawnComponentList);
        return root;
    }

    /**
     * Ends the game and displays the {@link Statistics}.
     *
     * @param gs the game session
     */
    @Override
    public void endGame(GameSession gs) {
        try {
            Stage stage = (Stage) tileGroup.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/layouts/stats.fxml"));
            Scene scene = new Scene(loader.load());
            StatsController controller = loader.getController();
            controller.setGameSession(gs);
            stage.setTitle("Quoridor");
            stage.getIcons().add(new Image("resources/icons/favicon.png"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the main menu.
     *
     * @param primaryStage the stage
     */
    private void loadMainMenu(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/layouts/mainmenu.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Quoridor");
            primaryStage.getIcons().add(new Image("resources/icons/favicon.png"));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the turn and updates appropriate labels.
     */
    @Override
    public void updateTurn(Player newTurnPlayer) {
        Platform.runLater(() -> {
            currentTurnLabel.setText(newTurnPlayer.getName() + "'s turn");
            currentTurnLabel.setTextFill(Color.valueOf(newTurnPlayer.getPawnColour()));
            wallsLabel.setText("Walls left: " + newTurnPlayer.getWalls());
            wallsLabel.setTextFill(Color.valueOf(newTurnPlayer.getPawnColour()));
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Player) {
            Player newTurnPlayer = (Player) arg;
            updateTurn(newTurnPlayer);
        } else if (arg instanceof GameSession) {
            endGame((GameSession)arg);
        }
    }
}
