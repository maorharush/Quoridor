package com.harush.zitoon.quoridor.core.model;

/**
 * Represents a Player in the game.
 */
public abstract class Player {
    private String name;
    private int walls;
    private Statistics stats;
    private String pawnColour;
    protected final Wall[][] verticalWalls;
    protected final Wall[][] horizontalWalls;
    protected final Pawn pawn;

    /**
     * Creates a new Player by initialising its name and pawn
     * @param name       player name
     * @param pawn       player pawn
     * @param pawnColour color of the pawn
     */
    public Player(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls, String pawnColour) {

        //Initialise values.
        this.name = name;
        this.verticalWalls = verticalWalls;
        this.horizontalWalls = horizontalWalls;
        walls = Settings.getSingleton().getWalls();
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
    public int getWalls() {
        return walls;
    }

    /**
     * Decrements the number of walls that the player has by one.
     *
     * @throws IllegalStateException if the number of walls is below 0
     */
    public void decrementWalls() {
        if (walls == 0) {
            throw new IllegalStateException("The number of walls cannot be decremented below 0.");
        }
        walls--;
        System.out.println(String.format("%s has %d walls left", name, walls));
    }

    /**
     * Increments the number of walls that a player has by one.
     *
     * @throws IllegalStateException if the number of walls are at maximum
     */
    public void incrementWalls() {
        if (walls == Settings.getSingleton().getWalls()) {
            throw new IllegalStateException("The number of walls are already at maximum.");
        }
        walls++;
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
