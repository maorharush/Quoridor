package com.harush.zitoon.quoridor.core.model;

import java.util.List;

public interface IGameSession {

    Player getCurrentPlayer();

    LogicResult isCurrentTurn(PawnType pawnType);

    Board getBoard();

    void checkForWinnerAndUpdateTurn(PlayerAction playerAction);

    List<Player> getPlayers();

    int getCurrentPlayerIndex();
}
