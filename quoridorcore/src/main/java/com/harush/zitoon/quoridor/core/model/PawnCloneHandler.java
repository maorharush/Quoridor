package com.harush.zitoon.quoridor.core.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class PawnCloneHandler implements CloneHandler<Pawn> {


    @Override
    public Pawn clone(Pawn pawn, Object... args) {
        Board clonedBoard = (Board) args[0];
        return new ClonedPawn(pawn, clonedBoard);
    }

    private class ClonedPawn implements Pawn {

        private final Pawn pawn;

        private final Board clonedBoard;

        private int currentX;

        private int currentY;

        public ClonedPawn(Pawn pawn, Board clonedBoard) {
            this.pawn = new PawnLogic(clonedBoard, pawn.getType());
            this.clonedBoard = clonedBoard;
            this.pawn.setInitialCoordinate(pawn.getInitialCoordinate());
            this.currentX = pawn.getX();
            this.currentY = pawn.getY();
        }

        @Override
        public LogicResult spawn(int x, int y) {
            throw new NotImplementedException();
        }

        @Override
        public LogicResult move(int x, int y) {
            clonedBoard.movePawn(currentX, currentY, x, y);
            currentX = x;
            currentY = y;
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
            return currentX;
        }

        @Override
        public int getY() {
            return currentY;
        }

        @Override
        public Coordinate getInitialCoordinate() {
            return pawn.getInitialCoordinate();
        }

        @Override
        public Coordinate getCurrentCoordinate() {
            return new Coordinate(currentX, currentY);
        }

        @Override
        public void setInitialCoordinate(Coordinate initialCoordinate) {
            throw new NotImplementedException();
        }

        @Override
        public void setCurrentCoordinate(Coordinate currentCoordinate) {
            currentX = currentCoordinate.getX();
            currentY = currentCoordinate.getY();
        }
    }
}
