package com.harush.zitoon.quoridor.core.model;

public interface WinnerDecider {

    boolean isWinner(Player player);

    boolean isWinner(Coordinate initialCoordinate, Coordinate currentCoordinate);
}
