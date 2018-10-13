package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.harush.zitoon.quoridor.core.model.Utils.PlayerHistory;
import com.harush.zitoon.quoridor.core.model.Utils.PlayersHistoryFactoryImpl;
import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.PlayerDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
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

public class PlayerHistoryFactoryTest {

    @Mock
    private PlayerDAO playerDAO;

    @Mock
    private GameRecDAO gameRecDAO;

    @InjectMocks
    private PlayersHistoryFactoryImpl playersHistoryFactory;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPlayerHistories_success() {

        PlayerDBO player1DBO = TestHelper.generatePlayerDBO(11, "player1", false);
        PlayerDBO player2DBO = TestHelper.generatePlayerDBO(22, "player2", true);

        when(playerDAO.getPlayer(eq(11))).thenReturn(player1DBO);
        when(playerDAO.getPlayer(eq(22))).thenReturn(player2DBO);

        GameRecDBO player1SpawnMove = TestHelper.generateGameRecDBO(1, 11, "RED",4, 8, -1, -1, null);
        GameRecDBO player1sFirstMove = TestHelper.generateGameRecDBO(1, 11,"RED", 4, 7, -1, -1, null);
        GameRecDBO player1SecondMove = TestHelper.generateGameRecDBO(1, 11,"RED", -1, -1, 1, 1, 'v');
        List<GameRecDBO> player1Moves = Lists.newArrayList(player1SpawnMove, player1sFirstMove, player1SecondMove);

        GameRecDBO player2SpawnMove = TestHelper.generateGameRecDBO(1, 22, "GREEN",4, 0, -1, -1, null);
        GameRecDBO player2FirstMove = TestHelper.generateGameRecDBO(1, 22, "GREEN",4, 1, -1, -1, null);
        GameRecDBO player2SecondMove = TestHelper.generateGameRecDBO(1, 22,"GREEN", 4, 2, -1, -1, null);
        List<GameRecDBO> player2Moves = Lists.newArrayList(player2SpawnMove, player2FirstMove, player2SecondMove);

        when(gameRecDAO.getPlayerRecords(eq(1), eq(11))).thenReturn(player1Moves);
        when(gameRecDAO.getPlayerRecords(eq(1), eq(22))).thenReturn(player2Moves);

        WallData player1WallPlacement = new WallData(1,1, false, true, null);
        List<WallData> player1WallPlacements = Lists.newArrayList(player1WallPlacement);

        PlayerHistory expectedPlayer1History = new PlayerHistory(11, "player1", PawnType.RED, player1WallPlacements, 9, 2, new Coordinate(4,8), new Coordinate(4,7), false);
        PlayerHistory expectedPlayer2History = new PlayerHistory(22, "player2", PawnType.GREEN, Lists.newArrayList(), 10, 2, new Coordinate(4,0), new Coordinate(4,2), true);
        List<PlayerHistory> expectedPlayerHistories = Lists.newArrayList(expectedPlayer1History, expectedPlayer2History);

        List<PlayerHistory> actualPlayerHistories = playersHistoryFactory.getPlayerHistories(1, Sets.newHashSet(11, 22));

        Assert.assertNotNull(actualPlayerHistories);
        Assert.assertEquals(expectedPlayerHistories.size(), actualPlayerHistories.size());
        expectedPlayerHistories.sort(Comparator.comparing(PlayerHistory::getPlayerID));
        actualPlayerHistories.sort(Comparator.comparing(PlayerHistory::getPlayerID));

        for (int i = 0; i < expectedPlayerHistories.size(); i++) {
            PlayerHistory expected = expectedPlayerHistories.get(i);
            PlayerHistory actual = actualPlayerHistories.get(i);
            Assert.assertEquals(expected, actual);
        }
    }


}
