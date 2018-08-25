package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.model.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    private static Board board;

    @Mock
    private Pawn pawn;


    @BeforeClass
    public static void beforeClass() {
        board = new Board();

    }

    @Test
    public void getAllWalls_success() {
        HumanPlayer player1 = new HumanPlayer("Maor", pawn, "color");
        HumanPlayer player2 = new HumanPlayer("Maor2", pawn, "color2");
        HumanPlayer player3 = new HumanPlayer("Maor3", pawn, "color3");

        List<Wall> expectedWalls = new ArrayList<Wall>() {{
            add(new Wall(0, 0, true, player1));
            add(new Wall(1, 1, true, player2));
            add(new Wall(2, 2, true, player3));
        }};

        board.setWall(0, 0, true, true, player1);
        board.setWall(1, 1, true, true, player2);
        board.setWall(2, 2, true, true, player3);

        List<Wall> actualAllWalls = board.getAllWalls();


        Assert.assertEquals("Number of walls returned is not as expected", expectedWalls.size(), actualAllWalls.size());

        Assert.assertEquals(expectedWalls, actualAllWalls);
    }

}
