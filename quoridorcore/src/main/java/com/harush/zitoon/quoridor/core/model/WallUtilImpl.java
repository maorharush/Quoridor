package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.List;

public class WallUtilImpl implements WallUtil {

    private final int width = Settings.getSingleton().getBoardWidth();
    private final int height = Settings.getSingleton().getBoardHeight();

    @Override
    public List<PlayerAction> findValidWallPlacements(Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();

        List<PlayerAction> verticalWallPlacements = findValidVerticalWallPlacements(verticalWalls);
        List<PlayerAction> horizontalWallPlacements = findValidHorizontalWallPlacements(horizontalWalls);

        wallPlacements.addAll(verticalWallPlacements);
        wallPlacements.addAll(horizontalWallPlacements);
        return wallPlacements;
    }

    private List<PlayerAction> findValidHorizontalWallPlacements(Wall[][] horizontalWalls) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                Wall wall = horizontalWalls[x][y];
                LogicResult logicResult = wall.validateWallPlacement();
                if (logicResult.isSuccess()) {
                    wallPlacements.add(new PlayerAction(x, y, true, true, null));
                }
            }
        }
        return wallPlacements;
    }

    private List<PlayerAction> findValidVerticalWallPlacements(Wall[][] verticalWalls) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                Wall wall = verticalWalls[x][y];
                LogicResult logicResult = wall.validateWallPlacement();
                if (logicResult.isSuccess()) {
                    wallPlacements.add(new PlayerAction(x, y, false, true, null));
                }
            }
        }
        return wallPlacements;
    }
}
