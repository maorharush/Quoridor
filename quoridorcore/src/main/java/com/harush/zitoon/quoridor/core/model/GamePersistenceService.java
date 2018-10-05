package com.harush.zitoon.quoridor.core.model;

public interface GamePersistenceService {

    void saveTurn(PlayerAction playerAction);

    GameSession loadGame();
}
