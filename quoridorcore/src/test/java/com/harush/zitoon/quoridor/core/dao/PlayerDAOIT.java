package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
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
    public void insertGameRecords() {
        List<PlayerDBO> dbos = createPlayerDBOS();
        playerDAO.insert(dbos);
        List<PlayerDBO> returnedPlayers = playerDAO.getAll();

        Assert.assertEquals("Size of players returned from DB was not as expected", dbos.size(), returnedPlayers.size());

        PlayerDBO expected = dbos.get(0);
        PlayerDBO actual = returnedPlayers.get(0);

        Assert.assertEquals(expected, actual);
    }

    private List<PlayerDBO> createPlayerDBOS() {
        List<PlayerDBO> dbos = new ArrayList<>();

        PlayerDBO playerDBO = new PlayerDBO();
        playerDBO.setPlayer_id(1);
        playerDBO.setHighest_score("3432");
        playerDBO.setIs_AI(0);
        playerDBO.setPlayer_name("Lord Mor");
        dbos.add(playerDBO);
        return dbos;
    }

}
