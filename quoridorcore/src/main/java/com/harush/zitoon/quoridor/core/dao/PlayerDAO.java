package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

public interface PlayerDAO extends DAO<PlayerDBO>{

    String TABLE_NAME = "players";

    PlayerDBO getPlayer(String playerName);
}
