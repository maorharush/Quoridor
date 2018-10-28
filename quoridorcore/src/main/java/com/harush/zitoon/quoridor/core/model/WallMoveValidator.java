package com.harush.zitoon.quoridor.core.model;

public interface WallMoveValidator {
    boolean isEnemyPathBlockedAfterWallMove(int wallPlaceInX, int wallPlacedInY, boolean isHorizontal, boolean isFirst);
}
