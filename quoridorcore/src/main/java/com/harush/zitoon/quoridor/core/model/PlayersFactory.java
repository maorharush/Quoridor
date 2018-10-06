package com.harush.zitoon.quoridor.core.model;

public interface PlayersFactory {

    Player getPlayer(String playerName, PawnType pawnType, boolean isAI, int initPawnX, int initPawnY, int pawnX, int pawnY, int numWallsLeft);
}