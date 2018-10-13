package com.harush.zitoon.quoridor.core.model.Utils;

import com.harush.zitoon.quoridor.core.model.Coordinate;
import com.harush.zitoon.quoridor.core.model.PawnType;
import com.harush.zitoon.quoridor.core.model.WallData;

import java.util.List;
import java.util.Objects;

/**
 * holds up the records of a game, by players.
 */
public class PlayerHistory {

    private int playerID;

    private String playerName;

    private PawnType pawnType;

    private List<WallData> wallPlacements;

    private int numWallsLeft;

    private Coordinate initialPawnCoordinate;

    private Coordinate currentPawnCoordinate;

    private int numTotalMoves;

    private boolean isAI;

    public PlayerHistory(int playerID, String playerName, PawnType pawnType, List<WallData> wallPlacements, int numWallsLeft, int numTotalMoves, Coordinate initialPawnCoordinate, Coordinate currentPawnCoordinate, boolean isAI) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.pawnType = pawnType;
        this.wallPlacements = wallPlacements;
        this.numWallsLeft = numWallsLeft;
        this.numTotalMoves = numTotalMoves;
        this.initialPawnCoordinate = initialPawnCoordinate;
        this.currentPawnCoordinate = currentPawnCoordinate;
        this.isAI = isAI;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PawnType getPawnType() {
        return pawnType;
    }

    public List<WallData> getWallPlacements() {
        return wallPlacements;
    }

    public int getNumWallsLeft() {
        return numWallsLeft;
    }

    public Coordinate getInitialPawnCoordinate() {
        return initialPawnCoordinate;
    }

    public Coordinate getCurrentPawnCoordinate() {
        return currentPawnCoordinate;
    }

    public boolean isAI() {
        return isAI;
    }

    public int getNumTotalMoves() {
        return numTotalMoves;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerHistory that = (PlayerHistory) o;
        return playerID == that.playerID &&
                numWallsLeft == that.numWallsLeft &&
                numTotalMoves == that.numTotalMoves &&
                isAI == that.isAI &&
                Objects.equals(playerName, that.playerName) &&
                pawnType == that.pawnType &&
                Objects.equals(wallPlacements, that.wallPlacements) &&
                Objects.equals(initialPawnCoordinate, that.initialPawnCoordinate) &&
                Objects.equals(currentPawnCoordinate, that.currentPawnCoordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, playerName, pawnType, wallPlacements, numWallsLeft, initialPawnCoordinate, currentPawnCoordinate, numTotalMoves, isAI);
    }

    @Override
    public String toString() {
        return "PlayerHistory{" +
                "playerID=" + playerID +
                ", playerName='" + playerName + '\'' +
                ", pawnType=" + pawnType +
                ", wallPlacements=" + wallPlacements +
                ", numWallsLeft=" + numWallsLeft +
                ", initialPawnCoordinate=" + initialPawnCoordinate +
                ", currentPawnCoordinate=" + currentPawnCoordinate +
                ", numTotalMoves=" + numTotalMoves +
                ", isAI=" + isAI +
                '}';
    }
}
