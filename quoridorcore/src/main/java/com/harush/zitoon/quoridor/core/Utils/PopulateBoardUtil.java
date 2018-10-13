package com.harush.zitoon.quoridor.core.Utils;

import com.harush.zitoon.quoridor.core.model.Board;

import java.util.List;

public interface PopulateBoardUtil {


    void populateBoard(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithPawns(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithWalls(Board board, List<PlayerHistory> playerHistories);
}
