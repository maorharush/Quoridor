package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.List;

public class WallUtilImpl implements WallUtil {

    private final int width = Settings.getSingleton().getBoardWidth();
    private final int height = Settings.getSingleton().getBoardHeight();

    @Override
    public List<PlayerAction> findValidWallPlacements(Board board, Player player) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();

        List<PlayerAction> verticalWallPlacements = findValidVerticalWallPlacements(board, player);
        List<PlayerAction> horizontalWallPlacements = findValidHorizontalWallPlacements(board, player);

        wallPlacements.addAll(verticalWallPlacements);
        wallPlacements.addAll(horizontalWallPlacements);
        return wallPlacements;
    }

    private List<PlayerAction> findValidHorizontalWallPlacements(Board board, Player player) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();
        for (int x = 0; x < width; x++) {
            for (int y = 1; y < height; y++) {
                Wall wall = new HorizontalWallLogic(x, y, board);
                LogicResult logicResult = wall.validateWallWithinBoard();
                if (logicResult.isSuccess()) {
                    wallPlacements.add(new PlayerAction(x, y, true, true, player));
                }
            }
        }
        return wallPlacements;
    }

    private List<PlayerAction> findValidVerticalWallPlacements(Board board, Player player) {
        List<PlayerAction> wallPlacements = Lists.newArrayList();
        for (int x = 0; x < width - 1; x++) {
            for (int y = 0; y < height; y++) {
                Wall wall = new VerticalWallLogic(x, y, board);
                LogicResult logicResult = wall.validateWallWithinBoard();
                if (logicResult.isSuccess()) {
                    wallPlacements.add(new PlayerAction(x, y, false, true, player));
                }
            }
        }
        return wallPlacements;
    }
}
