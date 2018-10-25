package com.harush.zitoon.quoridor.core.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class PawnCloneHandler implements CloneHandler<Pawn> {


    @Override
    public Pawn clone(Pawn pawn, Object... args) {
        return new ClonedPawn(pawn);
    }

    private class ClonedPawn implements Pawn {

        private final Pawn pawn;

        private int x;

        private int y;

        public ClonedPawn(Pawn pawn) {
            this.pawn = pawn;
        }

        @Override
        public LogicResult spawn(int x, int y) {
            throw new NotImplementedException();
        }

        @Override
        public LogicResult move(int x, int y) {
            throw new NotImplementedException();
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
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public Coordinate getInitialCoordinate() {
            return pawn.getInitialCoordinate();
        }

        @Override
        public Coordinate getCurrentCoordinate() {
            return new Coordinate(x, y);
        }

        @Override
        public void setInitialCoordinate(Coordinate initialCoordinate) {
            throw new NotImplementedException();
        }

        @Override
        public void setCurrentCoordinate(Coordinate currentCoordinate) {
            x = currentCoordinate.getX();
            y = currentCoordinate.getY();
        }
    }
}
