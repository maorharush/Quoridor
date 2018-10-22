package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class WallUtilTest {

    private WallUtil wallUtil;

    private int width;

    private int height;

    @Mock
    private GameSession gameSession;

    @Mock
    private Board board;

    @Mock
    Player player;

    @Before
    public void before() {
        wallUtil = new WallUtilImpl();
        width = Settings.getSingleton().getBoardWidth();
        height = Settings.getSingleton().getBoardHeight();

        MockitoAnnotations.initMocks(this);
        when(player.getNumWalls()).thenReturn(1);
        when(gameSession.getCurrentPlayer()).thenReturn(player);
        when(gameSession.getBoard()).thenReturn(board);
    }

    @Test
    public void generateValidWallPlacements_success() {
        Wall[][] verticalWalls = getVerticalWalls();
        Wall[][] horizontalWalls = getHorizontalWalls();

        when(board.containsWall(1,1,true)).thenReturn(true);
        when(board.containsWall(3, 4, false)).thenReturn(true);

        List<PlayerAction> expectedPlayerActions = Lists.newArrayList();
        List<PlayerAction> actualPlayerActions = wallUtil.generateValidWallPlacements(verticalWalls, horizontalWalls);

        Assert.assertEquals(expectedPlayerActions, actualPlayerActions);
    }

    public Wall[][] getHorizontalWalls() {
        Wall[][] verticalWalls = new HorizontalWallLogic[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                verticalWalls[x][y] = new HorizontalWallLogic(x, y, gameSession);
            }
        }
        return verticalWalls;
    }

    public Wall[][] getVerticalWalls() {
        Wall[][] verticalWalls = new VerticalWallLogic[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                verticalWalls[x][y] = new VerticalWallLogic(x, y, gameSession);
            }
        }
        return verticalWalls;
    }
}