package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface GamePersistenceService {

    void initGamePersistence(List<Player> players, GameSession gameSession);

    void saveTurn(int gameID, PlayerAction playerAction);

    int getCurrentPlayerIndex(int gameID);

    SavedGame loadGame();
}
