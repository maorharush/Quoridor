package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface PawnUtil {

    List<PlayerAction> generatePawnMoves(Player player);

    PlayerAction generatePawnMove(Player player);

    List<PlayerAction> generatePawnMoves(PlayerAction playerAction, Board board);
}

