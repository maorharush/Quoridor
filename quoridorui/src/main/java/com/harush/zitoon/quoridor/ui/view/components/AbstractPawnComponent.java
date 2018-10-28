package com.harush.zitoon.quoridor.ui.view.components;

import static com.harush.zitoon.quoridor.ui.view.MainGame.TILE_SIZE;

import com.harush.zitoon.quoridor.core.model.Settings;
import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.task.MovePawnTask;

import java.util.List;
import java.util.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
    public List<Coordinate> getValidMoves() {
        return pawn.getValidMoves();
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

    @Override
    public void setInitialCoordinate(Coordinate initialCoordinate) {
        pawn.setInitialCoordinate(initialCoordinate);
    }

    @Override
    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        final int newX = currentCoordinate.getX() * TILE_SIZE;
        final int newY = currentCoordinate.getY() * TILE_SIZE;
        relocate(newX, newY);
        this.currentX = newX;
        this.currentY = newY;
        pawn.setCurrentCoordinate(currentCoordinate);
    }

    @Override
    public void setBoard(Board board) {
        pawn.setBoard(board);
    }

    private void drawPawn() {
        StackPane pane = new StackPane();
        pane.setLayoutX(currentX-10);
        pane.setLayoutY(currentY-10);
        Image img = new Image("/resources/layouts/images/main/white-pawn.png",50,50,true,false,true);
        ImageView imgView = new ImageView(img);

        pane.getChildren().add(imgView);
        pane.boundsInParentProperty();
        getChildren().add(pane);


    }

    private void drawLabels() {
        if (Settings.getSingleton().isShowLabels()) {
            Text text = new Text(playerName);

            text.setLayoutX(currentX);
            text.setLayoutY(currentY);
            text.setFill(Color.valueOf(pawn.getType().getHexColor()));
            text.setTranslateX(45);//pushing the label away from currectX to  right side
            text.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
            getChildren().add(text);
        }
    }

    private void reverseMove() {
        relocate(currentX, currentY);
    }
}
