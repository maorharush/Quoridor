package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import com.harush.zitoon.quoridor.ui.view.task.PlaceWallTask;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a vertical wall component within the board using the {@link Rectangle} shape.
 */
public class VerticalWallComponent extends Rectangle implements Wall {

    private int currentX;

    private int currentY;

    private GameSession gameSession;

    private VerticalWallComponent[][] verticalWalls;

    private int width;

    private int height;

    private int nextWallY;

    private Wall wall;

    public VerticalWallComponent(int x, int y, VerticalWallComponent[][] verticalWalls, GameSession gameSession, Wall wall) {

        this.currentX = x;
        this.currentY = y;
        this.gameSession = gameSession;
        this.verticalWalls = verticalWalls;
        this.wall = wall;
        this.width = Settings.getSingleton().getBoardWidth();
        this.height = Settings.getSingleton().getBoardHeight();
        this.nextWallY = currentY + 1;


        setOnMouseEntered(e -> setWallColor(Color.valueOf(gameSession.getCurrentPlayer().getPawn().getType().getHexColor())));

        setOnMouseExited(e -> setWallColor(Color.rgb(153, 217, 234, 0.8)));

        setOnMousePressed(this::onMousePressed);

        drawWall(x, y);
    }

    @Override
    public LogicResult placeWall() {
        final String playerColor = gameSession.getCurrentPlayer().getPawn().getType().getHexColor();
        PlaceWallTask placeWallTask = new PlaceWallTask(wall);
        placeWallTask.setOnSucceeded((workerStateEvent) -> {
            LogicResult logicResult = (LogicResult) workerStateEvent.getSource().getValue();
            if (!logicResult.isSuccess()) {
                // Can display error message returned from logic layer in UI here by logicResult.getErrMsg();
                System.out.println(logicResult.getErrMsg());
                return;
            }
            setFill(Color.valueOf(playerColor));
            verticalWalls[currentX][currentY + 1].setFill(Color.valueOf(playerColor));
        });

        new Thread(placeWallTask).start();

        return new LogicResult(true);
    }

    @Override
    public LogicResult validateWallPlacement() {
        return wall.validateWallPlacement();
    }

    @Override
    public LogicResult validateWallWithinBoard() {
        return wall.validateWallWithinBoard();
    }

    private void onMousePressed(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            placeWall();
        }
    }

    private void setWallColor(Color color) {
        if (currentX == (width - 1)) {
            return;
        }
        if (nextWallY > 0 && nextWallY < height) {
            if (doesBoardContainWall()) {
                setFill(color);
                verticalWalls[currentX][nextWallY].setFill(color);
            }
        }
    }

    private boolean doesBoardContainWall() {
        return !gameSession.getBoard().containsWall(currentX, currentY, false) && !gameSession.getBoard().containsWall(currentX, nextWallY, false);
    }

    private void drawWall(int x, int y) {
        setWidth((double)MainGame.TILE_SIZE / 10);
        setHeight((double)MainGame.TILE_SIZE / 5 + 40);
        relocate((x * MainGame.TILE_SIZE) + 45, y * MainGame.TILE_SIZE);
        setFill(Color.rgb(153, 217, 234, 0.8));
    }
}

