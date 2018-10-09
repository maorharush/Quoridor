package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.List;
import java.util.Set;

public interface PlayersHistoryFactory {

    List<PlayerHistory> getPlayerHistories(int gameID, Set<Integer> playerNames);
}
