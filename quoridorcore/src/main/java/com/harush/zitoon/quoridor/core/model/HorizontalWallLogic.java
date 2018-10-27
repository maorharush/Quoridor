package com.harush.zitoon.quoridor.core.model;

public class HorizontalWallLogic implements Wall {

    private GameSession gameSession;

    private int currentX;

    private int currentY;

    private int width;

    private int height;

    private Board board;

    public HorizontalWallLogic(int x, int y, GameSession gameSession) {
        this.gameSession = gameSession;
        this.board = gameSession.getBoard();
        this.currentX = x;
        this.currentY = y;
        this.width = Settings.getSingleton().getBoardWidth();
        this.height = Settings.getSingleton().getBoardHeight();
    }

    public HorizontalWallLogic(int x, int y, Board board) {
        this.board = board;
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

        LogicResult validationResult = validateWallPlacement();
        if (!validationResult.isSuccess())  {
            return validationResult;
        }

        board.setWall(currentX, currentY, true, true, currentPlayer);
        System.out.println(String.format("1. %s placed wall at (%d,%d)", currentPlayerName, currentX, currentY));

        if (nextWallX > 0 && nextWallX < width) {
            board.setWall(nextWallX, currentY, true, false, currentPlayer);
            System.out.println(String.format("2. %s placed wall at (%d,%d)", currentPlayerName, nextWallX, currentY));
        }

        currentPlayer.getStatistics().incrementWallsUsed();
        currentPlayer.decrementWalls();

        PlayerAction playerAction = new PlayerAction(currentX, currentY, true, true, currentPlayer);
        gameSession.updateTurn(playerAction);

        return new LogicResult(true);
    }

    @Override
    public LogicResult validateWallPlacement() {
        if (gameSession.getCurrentPlayer().getNumWalls() == 0) {
            return new LogicResult(false, "You do not have any walls left.");
        }

        return validateWallWithinBoard();
    }

    @Override
    public LogicResult validateWallWithinBoard() {
        int nextX = currentX + 1;
        if (currentY == height || currentY == 0 || nextX == width) {
            return new LogicResult(false, "A horizontal wall cannot be placed at the very edge of the board");
        }

        if (board.containsWall(currentX, currentY, true) ||
                board.containsWall(nextX, currentY, true)) {
            return new LogicResult(false, "You cannot place a wall here.");
        }

        return new LogicResult(true);
    }
}
