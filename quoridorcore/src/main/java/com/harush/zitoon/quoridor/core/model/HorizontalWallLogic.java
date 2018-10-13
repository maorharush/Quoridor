package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.Utils.PlayerAction;
import com.harush.zitoon.quoridor.core.Utils.Settings;

public class HorizontalWallLogic implements Wall {

    private GameSession gameSession;

    private int currentX;

    private int currentY;

    private int width;

    private int height;

    public HorizontalWallLogic(int x, int y, GameSession gameSession) {
        this.gameSession = gameSession;
        this.currentX = x;
        this.currentY = y;
        this.width = Settings.getSingleton().getBoardWidth();
        this.height = Settings.getSingleton().getBoardHeight();
    }

    @Override
    public LogicResult placeWall() {
        int nextWallX = currentX + 1;
        Player currentPlayer = gameSession.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();

        if (nextWallX > width) {
            return new LogicResult(false, "A horizontal wall cannot be placed at the very top of the board");
        }

        if (currentX == width) {
            return new LogicResult(false, "A horizontal wall cannot be placed at the very edge of the board");
        }

        if (gameSession.getBoard().containsWall(currentX, currentY, true) ||
                gameSession.getBoard().containsWall(nextWallX, currentY, true)) {
            return new LogicResult(false, "You cannot place a wall here.");
        }

        if (currentPlayer.getNumWalls() == 0) {
            return new LogicResult(false, "You do not have any walls left.");
        }

        gameSession.getBoard().setWall(currentX, currentY, true, true, currentPlayer);
        System.out.println(String.format("1. %s placed wall at (%d,%d)", currentPlayerName, currentX, currentY));

        if (nextWallX > 0 && nextWallX < width) {
            gameSession.getBoard().setWall(nextWallX, currentY, true, false, currentPlayer);
            System.out.println(String.format("2. %s placed wall at (%d,%d)", currentPlayerName, nextWallX, currentY));
        }

        currentPlayer.getStatistics().incrementWallsUsed();
        currentPlayer.decrementWalls();

        PlayerAction playerAction = new PlayerAction(currentX, currentY, true, true, currentPlayer);
        gameSession.updateTurn(playerAction);

        return new LogicResult(true);
    }

    @Override
    public LogicResult removeWall() {
        return null;
    }
}
