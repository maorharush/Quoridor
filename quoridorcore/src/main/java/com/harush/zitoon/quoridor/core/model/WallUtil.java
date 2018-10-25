package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface WallUtil {

    List<PlayerAction> findValidWallPlacements(Wall[][] verticalWalls, Wall[][] horizontalWalls);
}
