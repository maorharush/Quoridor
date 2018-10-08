package com.harush.zitoon.quoridor.core.dao;

import com.google.common.collect.Lists;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.model.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAOIT {

    private PlayerDAO playerDAO;

    public PlayerDAOIT() {
        playerDAO = new PlayerDAOImpl();
    }

    @Before
    public void before() {
        playerDAO.deleteAll();
    }

    @Test
    public void insertGameRecords_success() {
        PlayerDBO lordMor = TestHelper.generatePlayerDBO(1, "Lord Mor", false);
        PlayerDBO aIPlayer = TestHelper.generatePlayerDBO(2, "AIPlayer", true);
        List<PlayerDBO> dbos = Lists.newArrayList(lordMor, aIPlayer);
        playerDAO.insert(dbos);
        List<PlayerDBO> returnedPlayers = playerDAO.getAll();

        Assert.assertNotNull(returnedPlayers);
        Assert.assertEquals("Size of players returned from DB was not as expected", dbos.size(), returnedPlayers.size());
        Assert.assertEquals(dbos, returnedPlayers);
    }

    @Test
    public void getPlayerByName_success() {
        PlayerDBO lordMor = TestHelper.generatePlayerDBO(1, "Lord Mor", false);
        PlayerDBO aIPlayer = TestHelper.generatePlayerDBO(2, "AIPlayer", true);
        List<PlayerDBO> dbos = Lists.newArrayList(lordMor, aIPlayer);
        playerDAO.insert(dbos);

        PlayerDBO returnedLordMor = playerDAO.getPlayer("Lord Mor");
        PlayerDBO returnedAIPlayer = playerDAO.getPlayer("AIPlayer");

        Assert.assertNotNull(returnedLordMor);
        Assert.assertEquals(lordMor, returnedLordMor);

        Assert.assertNotNull(returnedAIPlayer);
        Assert.assertEquals(aIPlayer, returnedAIPlayer);
    }

}
