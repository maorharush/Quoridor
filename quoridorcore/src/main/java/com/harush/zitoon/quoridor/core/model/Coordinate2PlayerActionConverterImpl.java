package com.harush.zitoon.quoridor.core.model;

public class Coordinate2PlayerActionConverterImpl implements Coordinate2PlayerActionConverter {

    @Override
    public PlayerAction toPlayerAction(Coordinate coordinate) {
        PlayerAction playerAction = new PlayerAction(coordinate.getX(), coordinate.getY(), null);
        return playerAction;
    }
}
