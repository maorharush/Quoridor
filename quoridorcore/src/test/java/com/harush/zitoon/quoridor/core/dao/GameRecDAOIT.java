package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

public class GameRecDAOIT {

    private GameRecDAO gameRecDAO;

    public GameRecDAOIT() {
        gameRecDAO = new GameRecDAOImpl();
    }

    @Before
    public void before() {
        gameRecDAO.deleteAll();
    }

    @Test
    public void insertGameRecords() {
        List<GameRecDBO> dbos = createGameRecDBOS();
        gameRecDAO.insert(dbos);
        List<GameRecDBO> returnedGameRecs = gameRecDAO.getAll();

        Assert.assertEquals("Size of game records returned from DB was not as expected", dbos.size(), returnedGameRecs.size());

        GameRecDBO expected = dbos.get(0);
        GameRecDBO actual = returnedGameRecs.get(0);

        Assert.assertEquals(expected, actual);
    }

    private List<GameRecDBO> createGameRecDBOS() {
        List<GameRecDBO> dbos = new ArrayList<>();

        GameRecDBO gameRecDBO = new GameRecDBO();
        gameRecDBO.setGame_id(1);
        gameRecDBO.setCur_row(1);
        gameRecDBO.setCur_col('h');
        gameRecDBO.setFence_col(-1);
        gameRecDBO.setFence_orien(null);
        gameRecDBO.setFence_row(-1);
        dbos.add(gameRecDBO);
        return dbos;
    }

}
