package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

public abstract class TestHelper {

    public static GameRecDBO generateGameRecDBO(int gameID, String playerName, int pawnX, int pawnY, int wallX, int wallY, Character orientation) {
        GameRecDBO gameRecDBO = new GameRecDBO();
        gameRecDBO.setGame_id(gameID);
        gameRecDBO.setPlayer_name(playerName);
        gameRecDBO.setPawn_x(pawnX);
        gameRecDBO.setPawn_y(pawnY);
        gameRecDBO.setWall_x(wallX);
        gameRecDBO.setWall_y(wallY);
        gameRecDBO.setFence_orien(orientation);
        return gameRecDBO;
    }
}
