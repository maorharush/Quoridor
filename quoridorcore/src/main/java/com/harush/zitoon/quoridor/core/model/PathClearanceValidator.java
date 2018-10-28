package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface PathClearanceValidator {
    boolean isPathClearToVictory(Board board, Player player, List<Coordinate> visitedCoordinates);
}
