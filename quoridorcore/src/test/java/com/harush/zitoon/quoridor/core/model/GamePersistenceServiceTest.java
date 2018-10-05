package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

public class GamePersistenceServiceTest {


    @Mock
    private GameRecDAO gameRecDAO;
    @Mock
    private PopulateBoardUtil populateBoardUtil;
    @Mock
    private PlayersHistoryFactory playersHistoryFactory;
    @Mock
    private PlayersFactory playersFactory;
    @Mock
    Player player;

    @InjectMocks
    private GamePersistenceServiceImpl gamePersistenceService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadGameAfterSpawn_success() {
        int gameID = 1;

        when(gameRecDAO.getMaxID()).thenReturn(gameID);

        GameRecDBO gameRecDBO1 = TestHelper.generateGameRecDBO(gameID, "player1", 4, 8, -1, -1, null);
        GameRecDBO gameRecDBO2 = TestHelper.generateGameRecDBO(gameID, "player2", 4, 0, -1, -1, null);
        List<GameRecDBO> gameRecords = Lists.newArrayList(gameRecDBO1, gameRecDBO2);

        when(gameRecDAO.getGameRecords(eq(gameID))).thenReturn(gameRecords);

        Player player1 = Mockito.mock(Player.class);
        Pawn pawn1 = Mockito.mock(Pawn.class);
        when(player1.getName()).thenReturn("player1");
        when(player1.getPawn()).thenReturn(pawn1);
        when(player1.getStatistics()).thenReturn(new Statistics());
        when(pawn1.getType()).thenReturn(PawnType.RED);
        Coordinate coordinate1 = new Coordinate(4, 8);
        when(pawn1.getCurrentCoordinate()).thenReturn(coordinate1);
        when(pawn1.getInitialCoordinate()).thenReturn(coordinate1);

        Player player2 = Mockito.mock(Player.class);
        Pawn pawn2 = Mockito.mock(Pawn.class);
        when(player2.getName()).thenReturn("player2");
        when(player2.getPawn()).thenReturn(pawn2);
        when(player2.getStatistics()).thenReturn(new Statistics());
        when(pawn2.getType()).thenReturn(PawnType.GREEN);
        Coordinate coordinate2 = new Coordinate(4, 0);
        when(pawn2.getCurrentCoordinate()).thenReturn(coordinate2);
        when(pawn2.getInitialCoordinate()).thenReturn(coordinate2);

        ArrayList<Player> players = Lists.newArrayList(player1, player2);

        Set<String> playerNames = Sets.newHashSet("player1", "player2");

        PlayerHistory playerHistory1 = new PlayerHistory(player1, Lists.newArrayList(gameRecDBO1));
        PlayerHistory playerHistory2 = new PlayerHistory(player2, Lists.newArrayList(gameRecDBO2));

        List<PlayerHistory> playerHistories = Lists.newArrayList(playerHistory1, playerHistory2);
        when(playersHistoryFactory.getPlayerHistories(eq(gameID), eq(playerNames))).thenReturn(playerHistories);

        Board board = new Board();
        board.setPawn(4, 8);
        board.setPawn(4, 0);

        when(populateBoardUtil.populateBoard(eq(playerHistories))).thenReturn(board);

        GameSession gameSession = gamePersistenceService.loadGame();

        int actualGameID = gameSession.getGameID();
        Player currentPlayer = gameSession.getCurrentPlayer();
        Board actualBoard = gameSession.getBoard();
        List<Player> actualPlayers = gameSession.getPlayers();
        Player winner = gameSession.getWinner();

        Assert.assertEquals(gameID, actualGameID);
        Assert.assertEquals(board, actualBoard);
        Assert.assertEquals(players, actualPlayers);
        Assert.assertNull(winner);
        Assert.assertEquals(player1, currentPlayer);
    }

}
