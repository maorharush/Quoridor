package com.harush.zitoon.quoridor.core.Utils;

import java.util.List;
import java.util.Set;

public interface PlayersHistoryFactory {

    List<PlayerHistory> getPlayerHistories(int gameID, Set<Integer> playerNames);
}
