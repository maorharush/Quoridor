package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainScreen;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static com.harush.zitoon.quoridor.ui.view.MainGame.TILE_SIZE;

/**
 * Represents a Pawn within the main game screen.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 */

public class PawnComponent extends StackPane {

    private PawnType type;
    private double mouseX, mouseY;
    private double oldX, oldY;
    private String color;
    private String playerName;
    private Pawn pawn;
    private MainScreen mainScreen;


    public PawnType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public PawnComponent(MainScreen mainScreen, PawnType type, int x, int y, Player player) {
        this.type = type;
        this.color = player.getPawnColour();
        this.playerName = player.getName();
        this.pawn = player.getPawn();
        this.mainScreen = mainScreen;

        boolean isSuccess = spawn(x, y);

        if (!isSuccess) {
            System.out.println(String.format("ERROR: Failed to spawn pawn in coordinate (%d,%d)", x, y));
            return;
        }

        drawPawn();
        drawLabels();

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY));

        setOnMouseReleased(e -> {
            int newX = mainScreen.toBoard(getLayoutX());
            int newY = mainScreen.toBoard(getLayoutY());

            System.out.println(type + " x:" + newX + " y:" + newY);

            boolean isMoveSuccess = move(newX, newY);
            if (isMoveSuccess) {
                mainScreen.checkForWinnerAndUpdateTurn(type, newX, newY);
            }
        });
    }

    private boolean spawn(int x, int y) {
        boolean isSuccess = pawn.spawn(x, y).isSuccess();

        if (isSuccess) {

            oldX = x * TILE_SIZE;
            oldY = y * TILE_SIZE;
            relocate(oldX, oldY);
        }

        return isSuccess;
    }

    private void drawLabels() {
        if (Settings.getSingleton().isShowLabels()) {
            Text text = new Text(playerName);
            text.setTranslateX(8);
            text.setTranslateY(-10);
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
            getChildren().add(text);
        }
    }

    private void drawPawn() {
        Circle ellipse = new Circle(TILE_SIZE * 0.3215);
        ellipse.setFill(Color.web(color));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);
        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);
        getChildren().addAll(ellipse);
    }

    private boolean move(int x, int y) {
        if (!mainScreen.isCurrentTurn(type)) {
            System.out.println(String.format("Not player %s's turn", playerName));
            reverseMove();
            return false;
        }

        LogicResult logicResult = pawn.move(x, y);

        if (!logicResult.isSuccess()) {
            logCannotMoveErrMsg(logicResult.getErrMsg());
            reverseMove();
            return false;
        }

        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);

        return true;
    }

    private void logCannotMoveErrMsg(String errMsg) {
        System.out.println(String.format("Moving player %s's pawn failed. Error message:[%s]", playerName, errMsg));
    }

    public void reverseMove() {
        relocate(oldX, oldY);
    }

    public enum PawnType {
        RED, WHITE, GREEN, BLUE;
    }

}
