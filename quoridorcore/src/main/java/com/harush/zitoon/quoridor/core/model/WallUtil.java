package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface WallUtil {

    List<PlayerAction> generateValidWallPlacements(Wall[][] verticalWalls, Wall[][] horizontalWalls);

    List<PlayerAction> gatherValidWallPlacements(boolean isHorizontal, Wall[][] walls);
}
