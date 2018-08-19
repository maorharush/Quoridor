package com.harush.zitoon.quoridor.core.model;


import java.util.logging.Logger;

public class PawnLogic implements Pawn {

    private static final Logger log = Logger.getLogger(PawnLogic.class.getSimpleName());

    private int x;

    private int y;

    private Board board;

    public PawnLogic(Board board) {
        this.board = board;
    }

    @Override
    public LogicResult move(int x, int y) {
        log.config(String.format("Moving to coordinate: (%d, %d)", x, y));

        if (!isValidMove(this.x, this.y, x, y)) {
            return createFailedLogicResult(String.format("Moving to: (%d, %d) is an invalid pawn move", x, y));
        }

        board.movePawn(this.x, this.y, x, y);
        this.x = x;
        this.y = y;

        return new LogicResult(true);
    }

    @Override
    public LogicResult spawn(int x, int y) {
        LogicResult logicResult = board.movePawn(this.x, this.y, x, y);

        if (logicResult.isSuccess()) {
            this.x = x;
            this.y = y;
        }
        return logicResult;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }


    private boolean isValidMove(int currentX, int currentY, int nextX, int nextY) {

        if (nextX >= board.getWidth() || nextY >= board.getHeight() || nextX < 0 || nextY < 0) { //Check if the new position is off the board
            return false;
        }
        if (board.getTile(nextX, nextY).containsPawn()) {
            return false;
        }
        if (nextX == currentX) {
            if (nextY == currentY - 1) { //going upwards
                if (board.containsWall(currentX, currentY, true)) {
                    return false;
                }
                return true;
            }
            if (nextY == currentY + 1) { //going downwards
                if (board.containsWall(currentX, currentY + 1, true)) {
                    return false;
                }
                return true;
            }
        }

        if (nextY == currentY) {
            if (nextX == currentX - 1) { //going left
                if (board.containsWall(currentX - 1, currentY, false)) {
                    return false;
                }
                return true;
            }
            if (nextX == currentX + 1) { //going right
                if (board.containsWall(currentX, currentY, false)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private LogicResult createFailedLogicResult(String errMsg) {
        return new LogicResult(false, errMsg);
    }
}
