package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.*;
import com.harush.zitoon.quoridor.ui.view.MainGame;
import com.harush.zitoon.quoridor.ui.view.task.PlaceWallTask;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a horizontal wall component within the board using the {@link Rectangle} shape.
 */
public class HorizontalWallComponent extends Rectangle implements Wall {

	private int currentX;

	private int currentY;

	private GameSession gameSession;

	private HorizontalWallComponent[][] horizontalWalls;

	private int width;

	private int height;

	private int nextWallX;

	private Wall wall;

	public HorizontalWallComponent(int x, int y, HorizontalWallComponent[][] horizontalWalls, GameSession gameSession, Wall wall) {

		this.currentX = x;
		this.currentY = y;
		this.nextWallX = x + 1;
		this.gameSession = gameSession;
		this.horizontalWalls = horizontalWalls;
		this.width = Settings.getSingleton().getBoardWidth();
		this.height = Settings.getSingleton().getBoardHeight();
		this.wall = wall;

		setOnMouseEntered(e -> setWallColor(Color.valueOf(gameSession.getCurrentPlayer().getPawn().getType().getHexColor())));

		setOnMouseExited(e -> setWallColor(Color.rgb(153, 217, 234, 0.8)));

		setOnMousePressed(this::onMousePressed);

		drawWall(x, y);
	}

	private void onMousePressed(MouseEvent e) {
		if (e.isPrimaryButtonDown()) {
			placeWall();
		}
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
			horizontalWalls[nextWallX][currentY].setFill(Color.valueOf(playerColor));
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

    private void setWallColor(Color color) {
		if (nextWallX > 0 && nextWallX < width) {
			if (doesBoardContainWall()) {
				setFill(color);
				horizontalWalls[nextWallX][currentY].setFill(color);
			}
		}
	}

	private boolean doesBoardContainWall() {
		return !gameSession.getBoard().containsWall(currentX, currentY, true) && !gameSession.getBoard().containsWall(nextWallX, currentY, true);
	}

	private void drawWall(int x, int y) {
		setWidth((double)MainGame.TILE_SIZE / 5 + 40);
		setHeight((double)MainGame.TILE_SIZE / 10);
		relocate(x * MainGame.TILE_SIZE, y * MainGame.TILE_SIZE);
		setFill(Color.rgb(153, 217, 234, 0.8));
	}

}
