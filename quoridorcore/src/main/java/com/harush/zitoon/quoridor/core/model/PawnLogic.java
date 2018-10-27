package com.harush.zitoon.quoridor.core.model;


import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public PawnLogic(Board board, PawnType type) {
        this.board = board;
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

        if (!isValidMove(newX, newY)) {
            String errMsg = String.format("%s failed to move pawn to (%d, %d): Invalid pawn move", currentPlayerName, newX, newY);
            System.out.println(errMsg);
            return createFailedLogicResult(errMsg);
        }

        System.out.println(String.format("%s is moving pawn to (%d, %d)", currentPlayerName, newX, newY));
        board.movePawn(currentX, currentY, newX, newY);
        currentX = newX;
        currentY = newY;

        PlayerAction playerAction = new PlayerAction(currentX, currentY, currentPlayer);
        gameSession.checkForWinnerAndUpdateTurn(playerAction);

        return new LogicResult(true);
    }

    @Override
    public List<Coordinate> getValidMoves() {
        List<Coordinate> potentialMoves = getPotentialsMoves();
        return potentialMoves.stream().filter(potentialMove -> isValidMove(potentialMove.getX(), potentialMove.getY())).collect(Collectors.toList());
    }

    private List<Coordinate> getPotentialsMoves() {

        // TODO Add diagonal potential moves as well

        Coordinate potentialMoveUp = getPotentialMoveUp();
        Coordinate potentialSpecialJumpUp = getPotentialSpecialJumpUp();
        Coordinate potentialMoveDown = getPotentialMoveDown();
        Coordinate potentialSpecialJumpDown = getPotentialSpecialJumpDown();
        Coordinate potentialMoveRight = getPotentialMoveRight();
        Coordinate potentialSpecialJumpRight = getPotentialSpecialJumpRight();
        Coordinate potentialMoveLeft = getPotentialMoveLeft();
        Coordinate potentialSpecialJumpLeft = getPotentialSpecialJumpLeft();

        return Lists.newArrayList(potentialMoveUp, potentialSpecialJumpUp,
                potentialMoveDown, potentialSpecialJumpDown,
                potentialMoveLeft, potentialSpecialJumpLeft,
                potentialMoveRight, potentialSpecialJumpRight);
    }

    private Coordinate getPotentialSpecialJumpLeft() {
        return new Coordinate(currentX - 2, currentY);
    }

    private Coordinate getPotentialMoveLeft() {
        return new Coordinate(currentX - 1, currentY);
    }

    private Coordinate getPotentialSpecialJumpRight() {
        return new Coordinate(currentX + 2, currentY);
    }

    private Coordinate getPotentialMoveRight() {
        return new Coordinate(currentX + 1, currentY);
    }

    private Coordinate getPotentialSpecialJumpDown() {
        return new Coordinate(currentX, currentY + 2);
    }

    private Coordinate getPotentialMoveDown() {
        return new Coordinate(currentX, currentY + 1);
    }

    private Coordinate getPotentialSpecialJumpUp() {
        return new Coordinate(currentX, currentY - 2);
    }

    private Coordinate getPotentialMoveUp() {
        return new Coordinate(currentX, currentY - 1);
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
        board.movePawn(currentX, currentY, newX, newY);
        this.currentX = newX;
        this.currentY = newY;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    private boolean isValidMove(int nextX, int nextY) {

        if (isOffBoard(nextX, nextY)) {
            return false;
        }
        if (isOccupied(nextX, nextY)) {
            return false;
        }

        if (isValidVerticalMove(currentX, currentY, nextX, nextY)) {
            return true;
        }

        return isValidHorizontalMove(currentX, currentY, nextX, nextY);
    }

    private Boolean isValidHorizontalMove(int currentX, int currentY, int nextX, int nextY) {
        if (isHorizontalMove(currentY, nextY)) {
            if (isValidMoveLeft(currentX, currentY, nextX, nextY)) {
                return true;
            }

            return isValidMoveRight(currentX, currentY, nextX, nextY);

        }
        return false;
    }

    private Boolean isValidMoveRight(int currentX, int currentY, int nextX, int nextY) {
        if (isMoveRight(currentX, nextX)) {
            if (isUnblocked(currentX, currentY, false)) {
                if (isSpecialJump(nextX - 1, nextY)) {
                    return isUnblocked(nextX - 1, nextY, false);
                }
                return nextX == currentX + 1;
            }
        }
        return false;
    }

    private Boolean isValidMoveLeft(int currentX, int currentY, int nextX, int nextY) {
        if (isMoveLeft(currentX, nextX)) {
            if (isUnblocked(currentX - 1, currentY, false)) {
                if (isSpecialJump(nextX + 1, nextY)) {
                    return isUnblocked(nextX, nextY, false);
                }
                return nextX == currentX - 1;
            }
        }
        return false;
    }

    private Boolean isValidVerticalMove(int currentX, int currentY, int nextX, int nextY) {
        if (isVerticalMove(currentX, nextX)) {
            if (isValidMoveUp(currentX, currentY, nextX, nextY)) {
                return true;
            }

            return isValidMoveDown(currentX, currentY, nextX, nextY);
        }
        return false;
    }

    private Boolean isValidMoveDown(int currentX, int currentY, int nextX, int nextY) {
        if (isMoveDown(currentY, nextY)) {
            if (isUnblocked(currentX, currentY + 1, true)) {
                if (isSpecialJump(nextX, nextY - 1)) {
                    return isUnblocked(nextX, nextY, true);
                }
                return nextY == currentY + 1;
            }
        }
        return false;
    }

    private Boolean isValidMoveUp(int currentX, int currentY, int nextX, int nextY) {
        if (isMoveUp(currentY, nextY)) {
            if (isUnblocked(currentX, currentY, true)) {
                if (isSpecialJump(nextX, nextY + 1)) {
                    return isUnblocked(nextX, nextY + 1, true);
                }
                return nextY == currentY - 1;
            }

        }
        return false;
    }

    private boolean isSpecialJump(int gapX, int gapY) {
        return board.containsPawn(gapX, gapY);
    }

    private boolean isOccupied(int nextX, int nextY) {
        return board.isOccupied(nextX, nextY);
    }

    private boolean isUnblocked(int currentX, int currentY, boolean isHorizontal) {
        return !board.containsWall(currentX, currentY, isHorizontal);
    }

    private boolean isMoveRight(int currentX, int nextX) {
        return nextX > currentX;
    }

    private boolean isMoveLeft(int currentX, int nextX) {
        return nextX < currentX;
    }

    private boolean isHorizontalMove(int currentY, int nextY) {
        return nextY == currentY;
    }

    private boolean isMoveDown(int currentY, int nextY) {
        return nextY > currentY;
    }

    private boolean isMoveUp(int currentY, int nextY) {
        return nextY < currentY;
    }

    private boolean isVerticalMove(int currentX, int nextX) {
        return nextX == currentX;
    }

    private boolean isOffBoard(int nextX, int nextY) {
        return nextX >= board.getWidth() || nextY >= board.getHeight() || nextX < 0 || nextY < 0;
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
