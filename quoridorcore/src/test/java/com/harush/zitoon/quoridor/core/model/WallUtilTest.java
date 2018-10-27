//package com.harush.zitoon.quoridor.core.model;
//
//import com.google.common.collect.Lists;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//
//public class WallUtilTest {
//
//    private WallUtil wallUtil;
//
//    private int width;
//
//    private int height;
//
//    @Mock
//    private GameSession gameSession;
//
//    @Mock
//    private Board board;
//
//    @Mock
//    Player player;
//
//    @Before
//    public void before() {
//        wallUtil = new WallUtilImpl();
//        width = Settings.getSingleton().getBoardWidth();
//        height = Settings.getSingleton().getBoardHeight();
//
//        MockitoAnnotations.initMocks(this);
//        when(player.getNumWalls()).thenReturn(1);
//        when(gameSession.getCurrentPlayer()).thenReturn(player);
//        when(gameSession.getBoard()).thenReturn(board);
//    }
//
//    @Test
//    public void findValidWallPlacements_allClear_success() {
//        Wall[][] verticalWalls = TestHelper.generateVerticalWalls(gameSession);
//        Wall[][] horizontalWalls = TestHelper.generateHorizontalWalls(gameSession);
//
//        List<PlayerAction> actualPlayerActions = wallUtil.findValidWallPlacements(verticalWalls, horizontalWalls);
//
//        Assert.assertEquals(128, actualPlayerActions.size());
//    }
//
//    @Test
//    public void findValidWallPlacements_1Vertical1HorizontalExist_success() {
//        Wall[][] verticalWalls = TestHelper.generateVerticalWalls(gameSession);
//        Wall[][] horizontalWalls = TestHelper.generateHorizontalWalls(gameSession);
//
//        when(board.containsWall(1,1,true)).thenReturn(true);
//        when(board.containsWall(3, 4, false)).thenReturn(true);
//
//        List<PlayerAction> actualPlayerActions = wallUtil.findValidWallPlacements(verticalWalls, horizontalWalls);
//
//        Assert.assertEquals(124, actualPlayerActions.size());
//    }
//}