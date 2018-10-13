package com.harush.zitoon.quoridor.core.model;

import java.util.List;
import java.util.Objects;

public class SavedGame {

    private int gameID;

    private int currentPlayerIndex;

    private List<PlayerHistory> playerHistories;

    public SavedGame(int gameID, int currentPlayerIndex, List<PlayerHistory> playerHistories) {
        this.gameID = gameID;
        this.currentPlayerIndex = currentPlayerIndex;
        this.playerHistories = playerHistories;
    }

    public int getGameID() {
        return gameID;
    }

    public List<PlayerHistory> getPlayerHistories() {
        return playerHistories;
    }

    public boolean isVsAI() {
        boolean is2PlayerGame = playerHistories.size() == 2;
        if (!is2PlayerGame) {
            return false;
        }

        return playerHistories.stream().anyMatch(PlayerHistory::isAI);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedGame savedGame = (SavedGame) o;
        return gameID == savedGame.gameID &&
                currentPlayerIndex == savedGame.currentPlayerIndex &&
                Objects.equals(playerHistories, savedGame.playerHistories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, currentPlayerIndex, playerHistories);
    }

    @Override
    public String toString() {
        return "SavedGame{" +
                "gameID=" + gameID +
                ", currentPlayerIndex=" + currentPlayerIndex +
                ", playerHistories=" + playerHistories +
                '}';
    }
}
