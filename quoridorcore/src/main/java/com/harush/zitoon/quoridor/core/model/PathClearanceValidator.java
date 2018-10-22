package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface PathClearanceValidator {
    boolean opponentPathIsClear(Board board, List<Player> players);
}
