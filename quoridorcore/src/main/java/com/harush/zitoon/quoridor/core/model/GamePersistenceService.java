package com.harush.zitoon.quoridor.core.model;

public interface GamePersistenceService {

    void saveGame(GameSession gameSession);

    GameSession loadGame();
}
