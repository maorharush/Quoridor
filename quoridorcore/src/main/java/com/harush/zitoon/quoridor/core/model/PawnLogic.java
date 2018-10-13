package com.harush.zitoon.quoridor.core.model;


import com.harush.zitoon.quoridor.core.Utils.PlayerAction;

import java.util.Objects;

public class PawnLogic implements Pawn {

    //private static final Logger log = Logger.getLogger(PawnLogic.class.getSimpleName());

    private int currentX;
    private int currentY;
    private Board board;
    private GameSession gameSession;
    private PawnType type;
    private Coordinate initialCoordinate;

    public PawnLogic(GameSession gameSession, PawnType type) {
        this.board = gameSession.getBoard();
        this.gameSession = gameSession;
        this.type = type;
    }

    @Override
    public LogicResult move(int newX, int newY) {
        Player currentPlayer = gameSession.getCurrentPlayer();
        String currentPlayerName = currentPlayer.getName();

        LogicResult isCurrentTurn = gameSession.isCurrentTurn(type);
        if (!isCurrentTurn.isSuccess()) {
            System.out.println(isCurrentTurn.getErrMsg());
            return isCurrentTurn;
        }

        if (!isValidMove(this.currentX, this.currentY, newX, newY)) {
            String errMsg = String.format("%s failed to move pawn to (%d, %d): Invalid pawn move", currentPlayerName, newX, newY);
            System.out.println(errMsg);
            return createFailedLogicResult(errMsg);
        }

        System.out.println(String.format("%s is moving pawn to (%d, %d)", currentPlayerName, newX, newY));
        board.movePawn(this.currentX, this.currentY, newX, newY);
        this.currentX = newX;
        this.currentY = newY;

        PlayerAction playerAction = new PlayerAction(currentX ,currentY, currentPlayer);
        gameSession.checkForWinnerAndUpdateTurn(playerAction);

        return new LogicResult(true);
    }

    @Override
    public PawnType getType() {
        return type;
    }

    @Override
    public int getX() {
        return currentX;
    }

    @Override
    public int getY() {
        return currentY;
    }

    @Override
    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    @Override
    public Coordinate getCurrentCoordinate() {
        return new Coordinate(currentX, currentY);
    }

    @Override
    public void setInitialCoordinate(Coordinate initialCoordinate) {
        this.initialCoordinate = initialCoordinate;
    }

    @Override
    public void setCurrentCoordinate(Coordinate currentCoordinate) {
        int newX = currentCoordinate.getX();
        int newY = currentCoordinate.getY();
        board.movePawn(currentX, currentY, newX, newY); //we recieve logicResult and doing nothing with it
        this.currentX = newX;
        this.currentY = newY;
    }

    @Override
    public LogicResult spawn(int x, int y) {
        LogicResult logicResult = board.movePawn(this.currentX, this.currentY, x, y);

        if (logicResult.isSuccess()) {
            this.currentX = x;
            this.currentY = y;
            initialCoordinate = new Coordinate(x, y);
        }
        return logicResult;
    }

    //TODO improve / replace with getValidMoves + Handle special move above enemy pawn
    private boolean isValidMove(int currentX, int currentY, int nextX, int nextY) {

        if (nextX >= board.getWidth() || nextY >= board.getHeight() || nextX < 0 || nextY < 0) { //Check if the new position is off the board
            return false;
        }
        if (board.getTile(nextX, nextY).containsPawn()) {
            return false;
        }
        if (nextX == currentX) {
            if (nextY == currentY - 1) { //going upwards
                return !board.containsWall(currentX, currentY, true);
            }
            if (nextY == currentY + 1) { //going downwards
                return !board.containsWall(currentX, currentY + 1, true);
            }
        }

        if (nextY == currentY) {
            if (nextX == currentX - 1) { //going left
                return !board.containsWall(currentX - 1, currentY, false);
            }
            if (nextX == currentX + 1) { //going right
                return !board.containsWall(currentX, currentY, false);
            }
        }
        return false;
    }

    private LogicResult createFailedLogicResult(String errMsg) {
        return new LogicResult(false, errMsg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PawnLogic pawnLogic = (PawnLogic) o;
        return currentX == pawnLogic.currentX &&
                currentY == pawnLogic.currentY &&
                Objects.equals(board, pawnLogic.board) &&
                Objects.equals(gameSession, pawnLogic.gameSession) &&
                type == pawnLogic.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentX, currentY, board, gameSession, type);
    }
}
