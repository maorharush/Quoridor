package com.harush.zitoon.quoridor.core.model;

public interface Wall {

    LogicResult placeWall();

    LogicResult validateWallPlacement();

    LogicResult validateWallWithinBoard();
}
