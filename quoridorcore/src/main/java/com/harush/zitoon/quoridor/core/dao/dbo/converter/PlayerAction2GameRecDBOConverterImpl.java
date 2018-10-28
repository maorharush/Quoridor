package com.harush.zitoon.quoridor.core.dao.dbo.converter;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.model.PlayerAction;

import static com.harush.zitoon.quoridor.core.model.PlayerActionType.MOVE_PAWN;

/** converting players action from core to DBO
 * used as independent class to operate with the core logic before database operation is been made.
 */
public class PlayerAction2GameRecDBOConverterImpl implements PlayerAction2GameRecDBOConverter {
    @Override
    /**
     * get the game record database object, to be used in saveGame();
     */
    public GameRecDBO toGameRecDBO(int gameID, PlayerAction playerAction) {
        GameRecDBO gameRecord = new GameRecDBO();
        gameRecord.setPlayer_id(playerAction.getPlayer().getPlayerID());
        gameRecord.setGame_id(gameID);

        if (playerAction.getPlayerActionType() == MOVE_PAWN) {
            gameRecord.setPawn_x(playerAction.getX());
            gameRecord.setPawn_type(playerAction.getPlayer().getPawn().getType().toString());
            gameRecord.setPawn_y(playerAction.getY());
            gameRecord.setWall_x(-1);
            gameRecord.setWall_y(-1);
            gameRecord.setIs_first(-1);
        } else {
            gameRecord.setWall_x(playerAction.getX());
            gameRecord.setWall_y(playerAction.getY());
            gameRecord.setPawn_x(-1);
            gameRecord.setPawn_y(-1);
            gameRecord.setFence_orien(playerAction.getIsHorizontal() ? 'h' : 'v');
            gameRecord.setIs_first(playerAction.getIsFirst() ? 1 : 0);
        }
        return gameRecord;
    }
}
