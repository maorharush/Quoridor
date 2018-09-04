package com.harush.zitoon.quoridor.core.model;

/**
 * Represents a Player in the game.
 */
public abstract class Player {
    private String name;
    private int numWalls;
    private Statistics stats;
    protected Wall[][] verticalWalls;
    protected Wall[][] horizontalWalls;
    protected Pawn pawn;

    /**
     *
     * @param name      player name
     * @param pawn      player pawn
     */
    public Player(String name, Pawn pawn) {
        this.name = name;
        this.pawn = pawn;
        this.stats = new Statistics();
        numWalls = Settings.getSingleton().getNumWalls();
    }

    /**
     * Creates a new Player by initialising its name and pawn
     * @param name       player name
     * @param pawn       player pawn
     */
    public Player(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        this.name = name;
        this.verticalWalls = verticalWalls;
        this.horizontalWalls = horizontalWalls;
        this.stats = new Statistics();
        this.pawn = pawn;
        numWalls = Settings.getSingleton().getNumWalls();
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

    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Get this player's {@link Statistics}
     *
     * @return the statistics
     */
    public Statistics getStatistics() {
        return stats;
    }
}
