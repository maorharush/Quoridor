package com.harush.zitoon.quoridor.core.Utils;

import com.harush.zitoon.quoridor.core.model.Player;

public interface WinnerDecider {

    boolean isWinner(Player player);
}
