package com.harush.zitoon.quoridor.ui.view.components;

import static com.harush.zitoon.quoridor.ui.view.MainGame.TILE_SIZE;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.task.MovePawnTask;
import java.util.Objects;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Represents a Pawn within the main game screen.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 */

public abstract class AbstractPawnComponent extends StackPane implements Pawn {

    private String playerName;
    double currentX, currentY;
    private Pawn pawn;

    AbstractPawnComponent(int x, int y, String playerName, Pawn pawn) {
        this.playerName = playerName;
        this.pawn = pawn;

        LogicResult logicResult = spawn(x, y);

        if (!logicResult.isSuccess()) {
            System.out.println(logicResult.getErrMsg());
            return;
        }

        drawPawn();
        drawLabels();
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
        final int newX = x * TILE_SIZE;
        final int newY = y * TILE_SIZE;
        relocate(newX, newY);

        MovePawnTask movePawnTask = new MovePawnTask(pawn, x, y);
        movePawnTask.setOnSucceeded((workerStateEvent) -> {
            LogicResult logicResult = (LogicResult)workerStateEvent.getSource().getValue();
            if (!logicResult.isSuccess()) {
                // Can display error message returned from logic layer in UI here by logicResult.getErrMsg();
                System.out.println(logicResult.getErrMsg());
                reverseMove();
                return;
            }

            currentX = newX;
            currentY = newY;
        });
        new Thread(movePawnTask).start();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPawnComponent that = (AbstractPawnComponent) o;
        return Objects.equals(pawn, that.pawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pawn);
    }

    @Override
    public Coordinate getInitialCoordinate() {
        return pawn.getInitialCoordinate();
    }

    @Override
    public Coordinate getCurrentCoordinate() {
        return pawn.getCurrentCoordinate();
    }

    private void drawPawn() {
        Circle ellipse = new Circle(TILE_SIZE * 0.3215);
        ellipse.setFill(Color.web(pawn.getType().getHexColor()));
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

    private void reverseMove() {
        relocate(currentX, currentY);
    }
}
