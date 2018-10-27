package com.harush.zitoon.quoridor.core.model;

public class VerticalWallLogic implements Wall{

    private GameSession gameSession;

    private int currentX;

    private int currentY;

    private int width;

    private int height;

    public VerticalWallLogic(int x, int y, GameSession gameSession) {
        this.gameSession = gameSession;
        this.currentX = x;
        this.currentY = y;
        this.width = Settings.getSingleton().getBoardWidth();
        this.height = Settings.getSingleton().getBoardHeight();
    }

    @Override
    public LogicResult placeWall() {
        int nextWallY = currentY + 1;
        Player currentPlayer = gameSession.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();
        WinnerDecider winnerDecider = new WinnerDeciderLogic();
        WallMoveValidatorImpl wallMoveValidator = new WallMoveValidatorImpl(gameSession, new PathClearanceValidatorImpl(winnerDecider));
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
        if(!wallMoveValidator.isEnemyPathBlockedAfterWallMove(currentX,currentY,false,true)){//TODO:should examine the use of isFirst here
            return  new LogicResult(false,"You cannot place a wall here, blocking opponents path to victory is illegal.");
        }
        gameSession.getBoard().setWall(currentX, currentY, false, true, currentPlayer);
        System.out.println(String.format("1. %s placed wall at (%d,%d)", currentPlayerName, currentX, currentY));

        if (currentX < width) {
            gameSession.getBoard().setWall(currentX, nextWallY, false, false, currentPlayer);
            System.out.println(String.format("2. %s placed wall at (%d,%d)", currentPlayerName, currentX, nextWallY));
        }
        currentPlayer.getStatistics().incrementWallsUsed();
        currentPlayer.decrementWalls();

        PlayerAction playerAction = new PlayerAction(currentX, currentY, false, true, currentPlayer);
        gameSession.updateTurn(playerAction);

        return new LogicResult(true);
    }



    @Override
    public LogicResult removeWall() {
        return null;
    }
}
