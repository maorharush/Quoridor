package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface WallUtil {

    List<PlayerAction> findValidWallPlacements(Board board, Player player);
}
