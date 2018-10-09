package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.model.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameDAOIT {

    private GameDAO gameDAO;

    public GameDAOIT() {
        gameDAO = new GameDAOImpl();
    }

    @Before
    public void before() {
        gameDAO.deleteAll();
    }

    @Test
    public void insertGameRecords() {
        List<GameDBO> dbos = createGameDBOS();
        gameDAO.insert(dbos);
        List<GameDBO> returnedPlayers = gameDAO.getAll();

        Assert.assertEquals("Size of games returned from DB was not as expected", dbos.size(), returnedPlayers.size());

        GameDBO expected = dbos.get(0);
        GameDBO actual = returnedPlayers.get(0);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLastGameID_success() {

        GameDBO gameDBO1 = TestHelper.generateGameDBO(1, -1, 12);
        GameDBO gameDBO2 = TestHelper.generateGameDBO(2, -1, 12);
        GameDBO gameDBO3 = TestHelper.generateGameDBO(3, -1, 12);

        gameDAO.insert(gameDBO1, gameDBO2, gameDBO3);

        int maxID = gameDAO.getLastGameID();
        Assert.assertEquals(3, maxID);
    }

    @Test
    public void getLastGame_success() {
        GameDBO gameDBO1 = TestHelper.generateGameDBO(1, -1, 12);
        GameDBO gameDBO2 = TestHelper.generateGameDBO(2, -1, 12);
        GameDBO gameDBO3 = TestHelper.generateGameDBO(3, -1, 12);

        gameDAO.insert(gameDBO1, gameDBO2, gameDBO3);

        GameDBO lastGame = gameDAO.getLastGame();

        Assert.assertEquals(gameDBO3, lastGame);
    }

    @Test
    public void noLastGameGetLastGameReturnsNull_success() {
        GameDBO lastGame = gameDAO.getLastGame();
        Assert.assertNull(lastGame);
    }

    @Test
    public void updateGameRecordWithWinner_success() {
        long startDate = System.currentTimeMillis() - 1000;
        GameDBO gameDBO = TestHelper.generateGameDBO(1, -1, 0);
        gameDBO.setStart_date(startDate);
        gameDAO.insert(gameDBO);

        GameDBO updated = TestHelper.generateGameDBO(1, 1, 12);
        updated.setStart_date(startDate);
        updated.setEnd_date(System.currentTimeMillis());

        gameDAO.updateGameRecord(updated);

        GameDBO lastGame = gameDAO.getLastGame();
        Assert.assertEquals(updated, lastGame);
    }

    private List<GameDBO> createGameDBOS() {
        List<GameDBO> dbos = new ArrayList<>();

        GameDBO gameDBO = new GameDBO();
        gameDBO.setGame_id(1);
        gameDBO.setStart_date(System.currentTimeMillis());
        gameDBO.setEnd_date(System.currentTimeMillis() + 10*30);
        gameDBO.setNum_of_moves(32);
        gameDBO.setWinner(1);
        dbos.add(gameDBO);
        return dbos;
    }

}
