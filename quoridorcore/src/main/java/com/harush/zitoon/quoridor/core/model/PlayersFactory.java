package com.harush.zitoon.quoridor.core.model;

public interface PlayersFactory {

    Player getPlayer(String playerName, boolean isAI, int pawnX, int pawnY, int numWallsLeft);
}