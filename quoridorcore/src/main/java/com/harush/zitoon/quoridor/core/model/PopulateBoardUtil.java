package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface PopulateBoardUtil {


    Board populateBoard(List<PlayerHistory> playerHistories);

    void populateBoardWithPawns(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithVerticalWalls(Board board, List<PlayerHistory> playerHistories);

    void populateBoardWithHorizontalWalls(Board board, List<PlayerHistory> playerHistories);
}
