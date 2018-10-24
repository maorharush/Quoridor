package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class PathClearanceValidatorImplTest {

    @Mock
    private GameSession gameSession;

    private PathClearanceValidator pathClearanceValidator;

    private Board board;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        board = new Board();
        when(gameSession.getBoard()).thenReturn(board);
        pathClearanceValidator = new PathClearanceValidatorImpl(new WinnerDeciderLogic());
    }

    @Test
    public void getPathClearanceValidatorImplTest_success() {

        WallData wallPlacement1 = new WallData(0, 4, true, true, null);
        WallData wallPlacement2 = new WallData(1, 4, true, false, null);
        WallData wallPlacement3 = new WallData(2, 4, true, true, null);
        WallData wallPlacement4 = new WallData(3, 4, true, false, null);
        WallData wallPlacement5 = new WallData(3, 2, false, true, null);
        WallData wallPlacement6 = new WallData(3, 3, false, false, null);
        WallData wallPlacement7 = new WallData(3, 0, false, true, null);
        WallData wallPlacement8 = new WallData(3, 1, false, false, null);
        List<WallData> wallPlacements = Lists.newArrayList(wallPlacement1, wallPlacement2, wallPlacement3, wallPlacement4, wallPlacement5, wallPlacement6, wallPlacement7, wallPlacement8);
        for (WallData wall : wallPlacements) {
            board.setWall(wall);
        }

        Player player = new HumanPlayer("maor", new PawnLogic(gameSession, PawnType.WHITE));
        when(gameSession.getCurrentPlayer()).thenReturn(player);
        when(gameSession.isCurrentTurn(eq(PawnType.WHITE))).thenReturn(new LogicResult(true));

        player.pawn.setInitialCoordinate(new Coordinate(4, 0));
        player.pawn.setCurrentCoordinate(new Coordinate(2, 2));
        List<Coordinate> validMovesList= new ArrayList<>();
        validMovesList.clear();
        Assert.assertTrue(pathClearanceValidator.isPathClearToVictory(board, player,validMovesList));


    }
}
