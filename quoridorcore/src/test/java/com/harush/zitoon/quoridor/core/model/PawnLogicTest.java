package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PawnLogicTest {

    private static final int BOARD_WIDTH = 9;

    private static final int BOARD_HEIGHT = 9;

    @InjectMocks
    public PawnLogic pawnLogic;

    @Mock
    private GameSession gameSession;

    @Mock
    private Board board;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        pawnLogic.setBoard(board);
        when(board.getHeight()).thenReturn(BOARD_HEIGHT);
        when(board.getWidth()).thenReturn(BOARD_WIDTH);
    }

    @Test
    public void getAllValidMovesFromInitialCoordinateBottom_success() {
        Coordinate initialBottomCoordinate = new Coordinate(BOARD_WIDTH / 2, BOARD_HEIGHT - 1);
        mockBoardOccupiedWithTile(initialBottomCoordinate);
        Coordinate initialTopCoordinate = new Coordinate(BOARD_WIDTH / 2, 0);
        mockBoardOccupiedWithTile(initialTopCoordinate);

        pawnLogic.setInitialCoordinate(initialBottomCoordinate);
        pawnLogic.setCurrentCoordinate(initialBottomCoordinate);

        Coordinate expectedValidMoveLeft = new Coordinate((BOARD_WIDTH / 2) - 1, BOARD_HEIGHT - 1);
        Coordinate expectedValidMoveRight = new Coordinate((BOARD_WIDTH / 2) + 1, BOARD_HEIGHT - 1);
        Coordinate expectedValidMoveUp = new Coordinate(BOARD_WIDTH / 2, BOARD_HEIGHT - 2);

        List<Coordinate> expectedValidMoves = Lists.newArrayList(expectedValidMoveLeft, expectedValidMoveRight, expectedValidMoveUp);
        List<Coordinate> actualValidMoves = pawnLogic.getValidMoves();

        Assert.assertNotNull(actualValidMoves);
        Assert.assertEquals(expectedValidMoves.size(), actualValidMoves.size());

        expectedValidMoves.sort(Comparator.comparing(Coordinate::getX).thenComparing(Coordinate::getY));
        actualValidMoves.sort(Comparator.comparing(Coordinate::getX).thenComparing(Coordinate::getY));

        Assert.assertEquals(expectedValidMoves, actualValidMoves);
    }

    private void mockBoardOccupiedWithTile(Coordinate occupiedCoordinate) {
        when(board.isOccupied(eq(occupiedCoordinate.getX()), eq(occupiedCoordinate.getY()))).thenReturn(true);
    }

}
