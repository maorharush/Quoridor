package com.harush.zitoon.quoridor.core.dao.dbo.converter;

import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import com.harush.zitoon.quoridor.core.model.Player;

public interface Player2PlayerDBOConverter {

    PlayerDBO toDBO(Player player);
}
