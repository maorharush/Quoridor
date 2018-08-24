package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.utils.UIUtils;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Objects;

import static com.harush.zitoon.quoridor.ui.view.MainGame.TILE_SIZE;

/**
 * Represents a Pawn within the main game screen.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 */

public class PawnComponent extends StackPane implements Pawn {

    private double mouseX, mouseY;
    private double currentX, currentY;
    private String color;
    private String playerName;
    private Pawn pawn;

    public PawnComponent(int x, int y, String color, String playerName, Pawn pawn) {
        this.color = color;
        this.playerName = playerName;
        this.pawn = pawn;

        LogicResult logicResult = spawn(x, y);

        if (!logicResult.isSuccess()) {
            System.out.println(logicResult.getErrMsg());
            return;
        }

        drawPawn();
        drawLabels();

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + currentX, e.getSceneY() - mouseY + currentY));

        setOnMouseReleased(e -> {
            int newX = UIUtils.toBoardCoordinate(getLayoutX());
            int newY = UIUtils.toBoardCoordinate(getLayoutY());

            move(newX, newY);
        });
    }

    @Override
    public LogicResult spawn(int x, int y) {
        LogicResult logicResult = pawn.spawn(x, y);

        if (logicResult.isSuccess()) {

            currentX = x * TILE_SIZE;
            currentY = y * TILE_SIZE;
            relocate(currentX, currentY);
        }
        return logicResult;
    }

    @Override
    public LogicResult move(int x, int y) {
        int newX = x * TILE_SIZE;
        int newY = y * TILE_SIZE;
        relocate(newX, newY);

        LogicResult logicResult = pawn.move(x, y);

        if (!logicResult.isSuccess()) {
            logCannotMoveErrMsg(logicResult.getErrMsg());
            reverseMove();
            return logicResult;
        }

        currentX = newX;
        currentY = newY;

        return new LogicResult(true);
    }

    @Override
    public PawnType getType() {
        return pawn.getType();
    }

    @Override
    public int getX() {
        return pawn.getX();
    }

    @Override
    public int getY() {
        return pawn.getY();
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

    private void drawLabels() {
        if (Settings.getSingleton().isShowLabels()) {
            Text text = new Text(playerName);
            text.setTranslateX(8);
            text.setTranslateY(-10);
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
            getChildren().add(text);
        }
    }

    private void logCannotMoveErrMsg(String errMsg) {
        System.out.println(String.format("Moving player %s's pawn failed. Error message:[%s]", playerName, errMsg));
    }

    private void reverseMove() {
        relocate(currentX, currentY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PawnComponent that = (PawnComponent) o;
        return Objects.equals(pawn, that.pawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pawn);
    }
}
