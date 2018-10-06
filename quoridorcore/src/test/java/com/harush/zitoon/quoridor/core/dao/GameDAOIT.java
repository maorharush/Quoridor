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

        GameDBO gameDBO1 = TestHelper.generateGameDBO(1, 0, 12);
        GameDBO gameDBO2 = TestHelper.generateGameDBO(2, 0, 12);
        GameDBO gameDBO3 = TestHelper.generateGameDBO(3, 0, 12);

        gameDAO.insert(gameDBO1, gameDBO2, gameDBO3);

        int maxID = gameDAO.getLastGameID();
        Assert.assertEquals(3, maxID);
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
