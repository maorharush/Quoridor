package com.harush.zitoon.quoridor.core.model.Utils;

import java.util.List;
import java.util.Set;

public interface PlayersHistoryFactory {

    List<PlayerHistory> getPlayerHistories(int gameID, Set<Integer> playerNames);
}
