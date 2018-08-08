package com.harush.zitoon.quoridor.core.dao.dbo;

import java.util.Objects;

public class PlayerDBO extends DBO {
    private String playerID;
    private String playerName;
    private String HighestScore;
    private int isAI;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getHighestScore() {
        return HighestScore;
    }

    public void setHighestScore(String highestScore) {
        HighestScore = highestScore;
    }

    public int getIsAI() {
        return isAI;
    }

    public void setIsAI(int isAI) {
        this.isAI = isAI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerDBO)) return false;
        PlayerDBO playerDBO = (PlayerDBO) o;
        return isAI == playerDBO.isAI &&
                Objects.equals(playerID, playerDBO.playerID) &&
                Objects.equals(playerName, playerDBO.playerName) &&
                Objects.equals(HighestScore, playerDBO.HighestScore);
    }

    @Override
    public int hashCode() {

        return Objects.hash(playerID, playerName, HighestScore, isAI);
    }


    @Override
    public String toString() {
        return "PlayerDBO{" +
                "playerID='" + playerID + '\'' +
                ", playerName='" + playerName + '\'' +
                ", HighestScore='" + HighestScore + '\'' +
                ", isAI=" + isAI +
                '}';
    }`
}
