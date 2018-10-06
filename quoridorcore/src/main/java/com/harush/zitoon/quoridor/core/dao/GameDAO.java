package com.harush.zitoon.quoridor.core.dao;


import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;

public interface GameDAO extends DAO<GameDBO> {

    String TABLE_NAME = "games";

    int getLastGameID();
}
