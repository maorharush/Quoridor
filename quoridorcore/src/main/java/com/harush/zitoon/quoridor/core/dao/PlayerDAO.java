package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

import java.util.List;

public interface PlayerDAO extends DAO<PlayerDBO>{

    String TABLE_NAME = "players";

    PlayerDBO getPlayer(int playerID);

    int insertAndReturnID(PlayerDBO playerDBO);
}
