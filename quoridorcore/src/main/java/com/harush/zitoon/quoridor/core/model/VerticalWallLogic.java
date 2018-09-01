package com.harush.zitoon.quoridor.core.model;

public class VerticalWallLogic implements Wall {

    private GameSession gameSession;

    private int currentX;

    private int currentY;

    private int width;

    private int height;

    public VerticalWallLogic(int x, int y, GameSession gameSession) {
        this.gameSession = gameSession;
        this.currentX = x;
        this.currentY = y;
        this.width = gameSession.getBoard().getWidth();
        this.height = gameSession.getBoard().getHeight();
    }

    @Override
    public LogicResult placeWall() {
        int nextWallY = currentY + 1;
        Player currentPlayer = gameSession.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();

        if (currentX == width || nextWallY == height) {
            return new LogicResult(false, "A vertical wall cannot be placed at the very top of the board");
        }

        if (gameSession.getBoard().containsWall(currentX, currentY, false) ||
                gameSession.getBoard().containsWall(currentX, nextWallY, false)) {
            return new LogicResult(false, "You cannot place a wall here.");
        }
        if (currentPlayer.getNumWalls() == 0) {
            return new LogicResult(false, "You do not have any walls left.");
        }
        gameSession.getBoard().setWall(currentX, currentY, false, true, currentPlayer);

        System.out.println(String.format("1. %s placed wall at (%d,%d)", currentPlayerName, currentX, currentY));
        if (currentX < width) {
            gameSession.getBoard().setWall(currentX, nextWallY, false, false, currentPlayer);
            System.out.println(String.format("2. %s placed wall at (%d,%d)", currentPlayerName, currentX, nextWallY));
        }
        currentPlayer.getStatistics().incrementWallsUsed();
        currentPlayer.decrementWalls();
        gameSession.updateTurn();

        return new LogicResult(true);
    }

    @Override
    public LogicResult removeWall() {
        return null;
    }
}
