package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.model.Utils.GamePersistenceServiceImpl;
import com.harush.zitoon.quoridor.core.model.Utils.PlayersHistoryFactory;
import com.harush.zitoon.quoridor.core.model.Utils.PopulateBoardUtil;
import com.harush.zitoon.quoridor.core.model.Utils.Statistics;
import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
    Player player;

    @InjectMocks
    private GamePersistenceServiceImpl gamePersistenceService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void loadGameAfterSpawn_success() {
//        int gameID = 1;
//
//        when(gameRecDAO.getLastGameID()).thenReturn(gameID);
//
//        GameRecDBO gameRecDBO1 = TestHelper.generateGameRecDBO(gameID, "player1", 4, 8, -1, -1, null);
//        GameRecDBO gameRecDBO2 = TestHelper.generateGameRecDBO(gameID, "player2", 4, 0, -1, -1, null);
//        List<GameRecDBO> gameRecords = Lists.newArrayList(gameRecDBO1, gameRecDBO2);
//
//        when(gameRecDAO.getGameRecords(eq(gameID))).thenReturn(gameRecords);
//
//        Player player1 = getPlayerMock("player1", PawnType.RED, 8);
//        Player player2 = getPlayerMock("player2", PawnType.GREEN, 0);
//        ArrayList<Player> players = Lists.newArrayList(player1, player2);
//        Set<String> playerNames = Sets.newHashSet("player1", "player2");
//
//        PlayerHistory playerHistory1 = new PlayerHistory(player1, Lists.newArrayList(gameRecDBO1));
//        PlayerHistory playerHistory2 = new PlayerHistory(player2, Lists.newArrayList(gameRecDBO2));
//
//        List<PlayerHistory> playerHistories = Lists.newArrayList(playerHistory1, playerHistory2);
//        when(playersHistoryFactory.getPlayerHistories(eq(gameID), eq(playerNames))).thenReturn(playerHistories);
//
//        Board board = new Board();
//        board.setPawn(4, 8);
//        board.setPawn(4, 0);
//
//        when(populateBoardUtil.populateBoard(eq(playerHistories))).thenReturn(board);
//
//        GameSession gameSession = gamePersistenceService.loadGame();
//
//        int actualGameID = gameSession.getGameID();
//        Player currentPlayer = gameSession.getCurrentPlayer();
//        Board actualBoard = gameSession.getBoard();
//        List<Player> actualPlayers = gameSession.getPlayers();
//        Player winner = gameSession.getWinner();
//
//        Assert.assertEquals(gameID, actualGameID);
//        Assert.assertEquals(board, actualBoard);
//        Assert.assertEquals(players, actualPlayers);
//        Assert.assertNull(winner);
//        Assert.assertEquals(player1, currentPlayer);
//    }

    private Player getPlayerMock(String player12, PawnType red, int i) {
        Player player1 = Mockito.mock(Player.class);
        Pawn pawn1 = Mockito.mock(Pawn.class);
        when(player1.getName()).thenReturn(player12);
        when(player1.getPawn()).thenReturn(pawn1);
        when(player1.getStatistics()).thenReturn(new Statistics());
        when(pawn1.getType()).thenReturn(red);
        Coordinate coordinate1 = new Coordinate(4, i);
        when(pawn1.getCurrentCoordinate()).thenReturn(coordinate1);
        when(pawn1.getInitialCoordinate()).thenReturn(coordinate1);
        return player1;
    }

}
