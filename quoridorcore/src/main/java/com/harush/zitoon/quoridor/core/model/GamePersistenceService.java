package com.harush.zitoon.quoridor.core.model;

public interface GamePersistenceService {

    void initGamePersistence(GameSession gameSession);

    void saveTurn(PlayerAction playerAction);

    SavedGame loadGame();
}
