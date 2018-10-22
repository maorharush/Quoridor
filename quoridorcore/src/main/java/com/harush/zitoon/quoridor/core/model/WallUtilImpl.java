package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.List;

public class WallUtilImpl implements WallUtil {

    private final int width = Settings.getSingleton().getBoardWidth();
    private final int height = Settings.getSingleton().getBoardHeight();

    @Override
    public List<PlayerAction> generateValidWallPlacements(Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();

        List<PlayerAction> verticalWallPlacements = gatherValidWallPlacements(false, verticalWalls);
        List<PlayerAction> horizontalWallPlacements = gatherValidWallPlacements(true, horizontalWalls);

        wallPlacements.addAll(verticalWallPlacements);
        wallPlacements.addAll(horizontalWallPlacements);
        return wallPlacements;
    }

    @Override
    public List<PlayerAction> gatherValidWallPlacements(boolean isHorizontal, Wall[][] walls) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Wall wall = walls[x][y];
                LogicResult logicResult = wall.validateWallPlacement();
                if (logicResult.isSuccess()) {
                    wallPlacements.add(new PlayerAction(x, y, isHorizontal, true, null));
                }
            }
        }
        return wallPlacements;
    }
}
