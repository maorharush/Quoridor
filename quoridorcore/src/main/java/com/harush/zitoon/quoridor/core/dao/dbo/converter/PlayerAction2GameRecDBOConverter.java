package com.harush.zitoon.quoridor.core.dao.dbo.converter;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.model.PlayerAction;

public interface PlayerAction2GameRecDBOConverter {

    GameRecDBO toGameRecDBO(int gameID, PlayerAction playerAction);
}
