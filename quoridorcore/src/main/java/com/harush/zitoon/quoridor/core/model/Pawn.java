package com.harush.zitoon.quoridor.core.model;


import java.util.List;

public interface Pawn {

    LogicResult spawn(int x, int y);

    LogicResult move(int x, int y);

    LogicResult move(Coordinate coordinate);

    List<Coordinate> getValidMoves();

    PawnType getType();

    int getX();

    int getY();

    Coordinate getInitialCoordinate();

    Coordinate getCurrentCoordinate();

    void setInitialCoordinate(Coordinate initialCoordinate);

    void setCurrentCoordinate(Coordinate currentCoordinate);
}
