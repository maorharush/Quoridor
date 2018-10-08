package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface PopulateBoardUtil {


    void populateBoard(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithPawns(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithWalls(Board board, List<PlayerHistory> playerHistories);
}
