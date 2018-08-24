package com.harush.zitoon.quoridor.core.model;

/**
 * Represents a Player in the game.
 */
public abstract class Player {
    private String name;
    private int numWalls;
    private Statistics stats;
    private String pawnColour;
    protected Pawn pawn;

    /**
     * Creates a new Player by initialising its name and pawn
     * @param name       player name
     * @param pawn       player pawn
     * @param pawnColour color of the pawn
     */
    public Player(String name, Pawn pawn, String pawnColour) {

        //Initialise values.
        this.name = name;
        numWalls = Settings.getSingleton().getWalls();
        this.stats = new Statistics();
        this.pawnColour = pawnColour;
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
        System.out.println(name + ": " + numWalls + " walls left ");
        numWalls--;
    }

    /**
     * Increments the number of walls that a player has by one.
     *
     * @throws IllegalStateException if the number of walls are at maximum
     */
    public void incrementWalls() {
        if (numWalls == Settings.getSingleton().getWalls()) {
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

    /**
     * Gets this player's Pawn {@Colour}
     *
     * @return the colour
     */
    public String getPawnColour() {
        return pawnColour;
    }


}
