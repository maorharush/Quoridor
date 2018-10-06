package com.harush.zitoon.quoridor.core.dao;

import com.google.common.collect.Lists;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.model.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void insertGameRecords_success() {
        List<GameRecDBO> dbos = createGameRecDBOS();
        gameRecDAO.insert(dbos);
        List<GameRecDBO> returnedGameRecs = gameRecDAO.getAll();

        Assert.assertEquals("Size of game records returned from DB was not as expected", dbos.size(), returnedGameRecs.size());

        GameRecDBO expected = dbos.get(0);
        GameRecDBO actual = returnedGameRecs.get(0);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSpecificGamesById_success() {
        GameRecDBO gameRecDBO1 = TestHelper.generateGameRecDBO(1, "player1", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO2 = TestHelper.generateGameRecDBO(1, "player2", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO3 = TestHelper.generateGameRecDBO(2, "player3", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO4 = TestHelper.generateGameRecDBO(3, "player4", -1, -1, -1, -1, null);

        gameRecDAO.insert(gameRecDBO1, gameRecDBO2, gameRecDBO3, gameRecDBO4);

        List<GameRecDBO> expectedGame1Records = Lists.newArrayList(gameRecDBO1, gameRecDBO2);

        List<GameRecDBO> game1Records = gameRecDAO.getGameRecords(1);
        List<GameRecDBO> game2Records = gameRecDAO.getGameRecords(2);
        List<GameRecDBO> game3Records = gameRecDAO.getGameRecords(3);

        Assert.assertNotNull(game1Records);
        Assert.assertNotNull(game2Records);
        Assert.assertNotNull(game3Records);

        Assert.assertFalse(game1Records.isEmpty());
        Assert.assertFalse(game2Records.isEmpty());
        Assert.assertFalse(game3Records.isEmpty());

        Assert.assertEquals(expectedGame1Records.size(), game1Records.size());
        Assert.assertEquals(expectedGame1Records, game1Records);
        Assert.assertEquals(gameRecDBO3, game2Records.get(0));
        Assert.assertEquals(gameRecDBO4, game3Records.get(0));
    }

    @Test
    public void getAllPlayerRecords_success() {
        GameRecDBO gameRecDBO1 = TestHelper.generateGameRecDBO(1, "player1", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO2 = TestHelper.generateGameRecDBO(1, "player1", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO3 = TestHelper.generateGameRecDBO(1, "player2", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO4 = TestHelper.generateGameRecDBO(1, "player2", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO5 = TestHelper.generateGameRecDBO(2, "player3", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO6 = TestHelper.generateGameRecDBO(2, "player4", -1, -1, -1, -1, null);

        gameRecDAO.insert(gameRecDBO1, gameRecDBO2, gameRecDBO3, gameRecDBO4, gameRecDBO5, gameRecDBO6);

        List<GameRecDBO> player1Records = gameRecDAO.getPlayerRecords(1, "player1");
        List<GameRecDBO> player4Records = gameRecDAO.getPlayerRecords(2, "player4");

        ArrayList<GameRecDBO> expectedPlayer1Records = Lists.newArrayList(gameRecDBO1, gameRecDBO2);

        Assert.assertNotNull(player1Records);
        Assert.assertNotNull(player4Records);

        Assert.assertFalse(player1Records.isEmpty());
        Assert.assertFalse(player4Records.isEmpty());

        Assert.assertEquals(expectedPlayer1Records, player1Records);
        Assert.assertEquals(gameRecDBO6, player4Records.get(0));
    }

    @Test
    public void getMaxGameID_success() {

        GameRecDBO gameRecDBO1 = TestHelper.generateGameRecDBO(1, "player1", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO2 = TestHelper.generateGameRecDBO(1, "player1", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO3 = TestHelper.generateGameRecDBO(1, "player2", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO4 = TestHelper.generateGameRecDBO(1, "player2", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO5 = TestHelper.generateGameRecDBO(2, "player3", -1, -1, -1, -1, null);
        GameRecDBO gameRecDBO6 = TestHelper.generateGameRecDBO(3, "player4", -1, -1, -1, -1, null);

        gameRecDAO.insert(gameRecDBO1, gameRecDBO2, gameRecDBO3, gameRecDBO4, gameRecDBO5, gameRecDBO6);

        int maxID = gameRecDAO.getMaxID();
        Assert.assertEquals(3, maxID);
    }

    private List<GameRecDBO> createGameRecDBOS() {
        List<GameRecDBO> dbos = new ArrayList<>();

        GameRecDBO gameRecDBO = new GameRecDBO();
        gameRecDBO.setGame_id(1);
        gameRecDBO.setPawn_y(1);
        gameRecDBO.setPawn_x('h');
        gameRecDBO.setWall_x(-1);
        gameRecDBO.setFence_orien(null);
        gameRecDBO.setWall_y(-1);
        dbos.add(gameRecDBO);
        return dbos;
    }

}
