package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

public interface PlayerAction2GameRecDBOConverter {

    GameRecDBO toGameRecDBO(PlayerAction playerAction);
}
