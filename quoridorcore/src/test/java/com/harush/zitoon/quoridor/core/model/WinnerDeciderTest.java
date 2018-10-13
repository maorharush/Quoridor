package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.Utils.WinnerDecider;
import com.harush.zitoon.quoridor.core.Utils.WinnerDeciderLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.mockito.Mockito.when;

public class WinnerDeciderTest {

    private static final int BOARD_WIDTH = 8;

    private static final int BOARD_HEIGHT = 8;

    private static WinnerDecider winnerDecider;

    @BeforeClass
    public static void beforeClass() {
        winnerDecider = new WinnerDeciderLogic();
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private Pawn pawn;

    @Test
    public void leftPlayerWins() {
        Coordinate initial = new Coordinate(0, BOARD_HEIGHT / 2);
        Coordinate current = new Coordinate(BOARD_WIDTH, getRandom(BOARD_HEIGHT));
        assertWinnerTrue(initial, current);
    }

    @Test
    public void rightPlayerWins() {
        Coordinate initial = new Coordinate(BOARD_WIDTH, BOARD_HEIGHT / 2);
        Coordinate current = new Coordinate(0, getRandom(BOARD_HEIGHT));
        assertWinnerTrue(initial, current);
    }

    @Test
    public void topPlayerWins() {
        Coordinate initial = new Coordinate(BOARD_WIDTH / 2, 0);
        Coordinate current = new Coordinate(getRandom(BOARD_WIDTH), BOARD_HEIGHT);
        assertWinnerTrue(initial, current);
    }

    @Test
    public void bottomPlayerWins() {
        Coordinate initial = new Coordinate(BOARD_WIDTH / 2, BOARD_HEIGHT);
        Coordinate current = new Coordinate(getRandom(BOARD_WIDTH), 0);
        assertWinnerTrue(initial, current);
    }

    @Test
    public void leftPlayerDoesNotWin() {
        Coordinate initial = new Coordinate(0, BOARD_HEIGHT / 2);
        Coordinate current = new Coordinate(0, 0);
        assertWinnerFalse(initial, current);
    }

    @Test
    public void rightPlayerDoesNotWin() {
        Coordinate initial = new Coordinate(BOARD_WIDTH, BOARD_HEIGHT / 2);
        Coordinate current = new Coordinate(BOARD_WIDTH, 0);
        assertWinnerFalse(initial, current);
    }

    @Test
    public void topPlayerDoesNotWin() {
        Coordinate initial = new Coordinate(BOARD_WIDTH / 2, 0);
        Coordinate current = new Coordinate(BOARD_WIDTH, 0);
        assertWinnerFalse(initial, current);
    }

    @Test
    public void bottomPlayerDoesNotWin() {
        Coordinate initial = new Coordinate(BOARD_WIDTH / 2, BOARD_HEIGHT);
        Coordinate current = new Coordinate(BOARD_WIDTH, BOARD_HEIGHT);
        assertWinnerFalse(initial, current);
    }

    private void assertWinnerTrue(Coordinate initial, Coordinate current) {
        boolean isWinner = isWinner(initial, current);
        Assert.assertTrue(isWinner);
    }

    private void assertWinnerFalse(Coordinate initial, Coordinate current) {
        boolean isWinner = isWinner(initial, current);
        Assert.assertFalse(isWinner);
    }

    private boolean isWinner(Coordinate initial, Coordinate current) {
        when(pawn.getInitialCoordinate()).thenReturn(initial);
        when(pawn.getCurrentCoordinate()).thenReturn(current);

        Player player = new HumanPlayer("TestPlayer", pawn);

        return winnerDecider.isWinner(player);
    }

    private int getRandom(int bound) {
        return new Random().nextInt(bound);
    }
}
