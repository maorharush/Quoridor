package com.harush.zitoon.quoridor.core.model.Utils;

import com.harush.zitoon.quoridor.core.model.GameSession;
import com.harush.zitoon.quoridor.core.model.Player;

import java.util.List;

public interface GamePersistenceService {

    void initGamePersistence(List<Player> players, GameSession gameSession);

    void saveTurn(int gameID, PlayerAction playerAction);

    SavedGame loadGame();
}
