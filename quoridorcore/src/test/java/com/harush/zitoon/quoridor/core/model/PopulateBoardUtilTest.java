package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import com.harush.zitoon.quoridor.core.Utils.PlayerHistory;
import com.harush.zitoon.quoridor.core.Utils.PopulateBoardUtil;
import com.harush.zitoon.quoridor.core.Utils.PopulateBoardUtilImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class PopulateBoardUtilTest {

    private static PopulateBoardUtil populateBoardUtil;

    @BeforeClass
    public static void beforeClass() {
        populateBoardUtil = new PopulateBoardUtilImpl();
    }

    @Test
    public void populateBoard_success() {
        Board board = new Board();

        WallData wallPlacement1 = new WallData(1, 1, true, true, null);
        WallData wallPlacement2 = new WallData(3, 3, false, true, null);
        ArrayList<WallData> wallPlacements = Lists.newArrayList(wallPlacement1, wallPlacement2);

        PlayerHistory humanPlayerHistory = new PlayerHistory(1,"player1", PawnType.RED, wallPlacements,
                8,
                2,
                new Coordinate(4, 8),
                new Coordinate(4, 8), false);

        PlayerHistory aiPlayerHistory = new PlayerHistory(2, "Wall-e", PawnType.BLUE, null,
                10,
                2,
                new Coordinate(4,0),
                new Coordinate(4, 2), true);

        ArrayList<PlayerHistory> playerHistories = Lists.newArrayList(humanPlayerHistory, aiPlayerHistory);

        populateBoardUtil.populateBoard(board, playerHistories);

        Board expectedBoard = new Board();
        expectedBoard.setPawn(4,8);
        expectedBoard.setPawn(4, 2);
        expectedBoard.setWall(1,1, true, true, null);
        expectedBoard.setWall(2,1, true, false, null);
        expectedBoard.setWall(3,3, false, true, null);
        expectedBoard.setWall(3,4, false, false, null);

        Assert.assertEquals(expectedBoard, board);
    }
}
