package com.harush.zitoon.quoridor.core.model;


public interface MainScreen {

    void endGame(GameSession gs);

    void updateTurn(Player newTurnPlayer);
}
