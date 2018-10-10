package com.harush.zitoon.quoridor.core.dao.dbo.converter;

import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.model.Player;

public class Player2PlayerDBOConverterImpl implements Player2PlayerDBOConverter {
    @Override
    public PlayerDBO toDBO(Player player) {
        PlayerDBO playerDBO = new PlayerDBO();
        playerDBO.setPlayer_name(player.getName());
        playerDBO.setIs_AI(player.isAI() ? 1 : 0);
        return playerDBO;
    }
}
