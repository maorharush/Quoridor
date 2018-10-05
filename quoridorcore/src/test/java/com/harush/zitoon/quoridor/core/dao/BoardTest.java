package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.model.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {


    @Mock
    private Pawn pawn;

    @Test
    public void getAllWalls3Walls_success() {
        Board board = new Board();

        HumanPlayer player1 = new HumanPlayer("Maor", pawn);
        HumanPlayer player2 = new HumanPlayer("Maor2", pawn);
        HumanPlayer player3 = new HumanPlayer("Maor3", pawn);

        List<WallData> expectedWalls = new ArrayList<WallData>() {{
            add(new WallData(0, 0, true, player1));
            add(new WallData(1, 1, true, player2));
            add(new WallData(2, 2, true, player3));
        }};

        board.setWall(0, 0, true, true, player1);
        board.setWall(1, 1, true, true, player2);
        board.setWall(2, 2, true, true, player3);

        List<WallData> actualAllWalls = board.getAllWalls();


        Assert.assertEquals("Number of walls returned is not as expected", expectedWalls.size(), actualAllWalls.size());
        Assert.assertEquals(expectedWalls, actualAllWalls);
    }

    @Test
    public void getAllWalls0Walls_success() {
        Board board = new Board();
        List<WallData> actualWalls = board.getAllWalls();
        Assert.assertEquals("The wall list was expected to be empty",0, actualWalls.size());
    }

}
