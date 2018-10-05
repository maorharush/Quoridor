package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import static com.harush.zitoon.quoridor.core.model.PlayerActionType.MOVE_PAWN;

public class PlayerAction2GameRecDBOConverterImpl implements PlayerAction2GameRecDBOConverter {
    @Override
    public GameRecDBO toGameRecDBO(PlayerAction playerAction) {
        GameRecDBO gameRecord = new GameRecDBO();
        gameRecord.setPlayer_name(playerAction.getPlayer().getName());
        if (playerAction.getPlayerActionType() == MOVE_PAWN) {
            gameRecord.setPawn_x(playerAction.getX());
            gameRecord.setPawn_y(playerAction.getY());
            gameRecord.setWall_x(-1);
            gameRecord.setWall_y(-1);
        } else {
            gameRecord.setWall_x(playerAction.getX());
            gameRecord.setWall_y(playerAction.getY());
            gameRecord.setPawn_x(-1);
            gameRecord.setPawn_y(-1);
            gameRecord.setFence_orien(playerAction.getWall_orien());
        }
        return gameRecord;
    }
}
