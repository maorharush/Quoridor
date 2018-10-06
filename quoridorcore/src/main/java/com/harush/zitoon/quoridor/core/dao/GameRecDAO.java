package com.harush.zitoon.quoridor.core.dao;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.List;

public interface GameRecDAO extends DAO<GameRecDBO> {

    String TABLE_NAME = "game_recorder";

    int getLastGameID();

    List<GameRecDBO> getGameRecords(int gameId);

    List<GameRecDBO> getPlayerRecords(int gameId, String playerName);
}
