package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.model.Utils.Settings;
import com.harush.zitoon.quoridor.core.model.Utils.Statistics;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a Player in the game.
 */
public abstract class Player {
    private int playerID;
    private String name;
    private int numWalls;
    private Statistics stats;
    private boolean isAI;
    protected Wall[][] verticalWalls;
    protected Wall[][] horizontalWalls;
    protected Pawn pawn;

    public Player(String name, int numWalls, Statistics stats, boolean isAI, Pawn pawn) {
        this.name = name;
        this.numWalls = numWalls;
        this.stats = stats;
        this.isAI = isAI;
        this.pawn = pawn;
    }

    public Player(String name, int numWalls, Statistics stats, boolean isAI, Wall[][] verticalWalls, Wall[][] horizontalWalls, Pawn pawn) {
        this.name = name;
        this.numWalls = numWalls;
        this.stats = stats;
        this.isAI = isAI;
        this.verticalWalls = verticalWalls;
        this.horizontalWalls = horizontalWalls;
        this.pawn = pawn;
    }

    /**
     *
     * @param name      player name
     * @param pawn      player pawn
     */
    public Player(String name, Pawn pawn, boolean isAI) {
        this.name = name;
        this.pawn = pawn;
        this.stats = new Statistics();
        this.isAI = isAI;
        numWalls = Settings.getSingleton().getNumWalls();

    }
    /**
     * Creates a new Player by initialising its name and pawn
     * @param name       player name
     * @param pawn       player pawn
     */
    public Player(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls, boolean isAI) {
        this.name = name;
        this.verticalWalls = verticalWalls;
        this.horizontalWalls = horizontalWalls;
        this.stats = new Statistics();
        this.pawn = pawn;
        this.isAI = isAI;
        numWalls = Settings.getSingleton().getNumWalls();
    }

    public Player(Pawn pawn) {
        this.pawn = pawn;
    }

    public abstract void play();

    /**
     * Gets the name of the player.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the amount of walls that the player has.
     *
     * @return the number of walls
     */
    public int getNumWalls() {
        return numWalls;
    }

    /**
     * Decrements the number of walls that the player has by one.
     *
     * @throws IllegalStateException if the number of walls is below 0
     */
    public void decrementWalls() {
        if (numWalls == 0) {
            throw new IllegalStateException("The number of walls cannot be decremented below 0.");
        }
        numWalls--;
        System.out.println(String.format("%s has %d walls left", name, numWalls));
    }

    /**
     * Increments the number of walls that a player has by one.
     *
     * @throws IllegalStateException if the number of walls are at maximum
     */
    public void incrementWalls() {
        if (numWalls == Settings.getSingleton().getNumWalls()) {
            throw new IllegalStateException("The number of walls are already at maximum.");
        }
        numWalls++;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public void setNumWalls(int numWalls) {
        this.numWalls = numWalls;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public boolean isAI() {
        return isAI;
    }

    /**
     * Get this player's {@link Statistics}
     *
     * @return the statistics
     */
    public Statistics getStatistics() {
        return stats;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID &&
                numWalls == player.numWalls &&
                isAI == player.isAI &&
                Objects.equals(name, player.name) &&
                Objects.equals(stats, player.stats) &&
                Arrays.equals(verticalWalls, player.verticalWalls) &&
                Arrays.equals(horizontalWalls, player.horizontalWalls) &&
                Objects.equals(pawn, player.pawn);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(playerID, name, numWalls, stats, isAI, pawn);
        result = 31 * result + Arrays.hashCode(verticalWalls);
        result = 31 * result + Arrays.hashCode(horizontalWalls);
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerID=" + playerID +
                ", name='" + name + '\'' +
                ", numWalls=" + numWalls +
                ", stats=" + stats +
                ", isAI=" + isAI +
                ", verticalWalls=" + Arrays.toString(verticalWalls) +
                ", horizontalWalls=" + Arrays.toString(horizontalWalls) +
                ", pawn=" + pawn +
                '}';
    }
}
