package com.harush.zitoon.quoridor.core.model.Utils;

import com.harush.zitoon.quoridor.core.model.Board;
import com.harush.zitoon.quoridor.core.model.WallData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * represent worker class that filled up the board with recent saved game data.
 */
public class PopulateBoardUtilImpl implements PopulateBoardUtil {

    /**
     * init a board instance with logical walls, pawns.
     *
     * @param playerHistories
     * @return Board
     */
    @Override
    public void populateBoard(Board board, List<PlayerHistory> playerHistories) {
        populateBoardWithWalls(board, playerHistories);
        populateBoardWithPawns(board, playerHistories);
    }

    /**
     * populating the board with logical pawns.
     *
     * @param board           new board
     * @param playerHistories from DAO
     */
    @Override
    public void populateBoardWithPawns(Board board, List<PlayerHistory> playerHistories) {
        playerHistories.forEach(playerHistory -> board.setPawn(playerHistory.getCurrentPawnCoordinate()));
    }

    @Override
    public void populateBoardWithWalls(Board board, List<PlayerHistory> playerHistories) {
        List<WallData> wallData = playerHistories.stream().
                filter(playerHistory -> playerHistory.getWallPlacements() != null).     //null? not -1?
                flatMap(playerHistory -> playerHistory.getWallPlacements().stream())
                .collect(Collectors.toList());

        for (WallData wallDatum : wallData) {
            if (wallDatum.isHorizontal()) {
                board.setWall(wallDatum);
                WallData wallData2 = new WallData(wallDatum.getX() + 1, wallDatum.getY(), true, false, null);
                board.setWall(wallData2);
            } else {
                board.setWall(wallDatum);
                WallData wallData2 = new WallData(wallDatum.getX(), wallDatum.getY() + 1, false, false, null);
                board.setWall(wallData2);
            }
        }
    }
}
