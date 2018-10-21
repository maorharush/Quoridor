package com.harush.zitoon.quoridor.core.dao;

import com.google.common.collect.Lists;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.model.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

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
    public void insertAndReturnID_success() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        jdbcTemplate.execute("DELETE FROM main.sqlite_sequence WHERE  main.sqlite_sequence.name='" + PlayerDAO.TABLE_NAME + "'");

        PlayerDBO lordMor = TestHelper.generatePlayerDBO("Lord Mor", false);
        int returnID = playerDAO.insertAndReturnID(lordMor);
        Assert.assertEquals(1, returnID);
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
        PlayerDBO lordMor = TestHelper.generatePlayerDBO(0,"Lord Mor", false);
        PlayerDBO aIPlayer = TestHelper.generatePlayerDBO(1, "AIPlayer", true);
        List<PlayerDBO> dbos = Lists.newArrayList(lordMor, aIPlayer);
        playerDAO.insert(dbos);

        PlayerDBO returnedLordMor = playerDAO.getPlayer(0);
        PlayerDBO returnedAIPlayer = playerDAO.getPlayer(1);

        Assert.assertNotNull(returnedLordMor);
        Assert.assertEquals(lordMor, returnedLordMor);

        Assert.assertNotNull(returnedAIPlayer);
        Assert.assertEquals(aIPlayer, returnedAIPlayer);
    }

}
