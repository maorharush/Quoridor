package com.harush.zitoon.quoridor.core.model;

public interface GamePersistenceService {

    void saveTurn(GameSession gameSession);

    GameSession loadGame();
}
