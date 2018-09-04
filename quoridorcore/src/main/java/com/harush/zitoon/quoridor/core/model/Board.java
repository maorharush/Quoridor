package com.harush.zitoon.quoridor.core.model;

/**
 * Represents a board filled with tiles.
 *
 * @author Moar Harush
 * @version 0.5
 */
public class Board {

    private Tile[][] tiles;
    private WallData[][] horizontalWallData;
    private WallData[][] verticalWallData;
    private int height;
    private int width;

    public Board() {
        this(9, 9);
    }

    public Board(int height, int width) {
        if ((height % 2 == 0) || (width % 2 == 0)) {
            throw new IllegalStateException("Height or width of the board cannot be even.");
        }
        this.tiles = new Tile[height][width];
        this.horizontalWallData = new WallData[height][width];
        this.verticalWallData = new WallData[height][width];
        this.height = height;
        this.width = width;
        setTilePositions();
    }

    private void setTilePositions() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = new Tile(x, y);
                tiles[x][y] = tile;
            }
        }
    }

    /**
     * Gets a tile with a given coordinate.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the tile
     * @throws IllegalArgumentException if x or y is below 0
     */
    public Tile getTile(int x, int y) {
        validateCoordinate(x, y);
        return tiles[x][y];
    }

    public LogicResult movePawn(int currentX, int currentY, int nextX, int nextY) {
        LogicResult logicResultCurrent = validateCoordinate(currentX, currentY);
        LogicResult logicResultNext = validateCoordinate(nextX, nextY);

        if (!logicResultCurrent.isSuccess()) {
            return logicResultCurrent;
        }

        if (!logicResultNext.isSuccess()) {
            return logicResultNext;
        }

        getTile(currentX, currentY).setContainsPawn(false);
        getTile(nextX, nextY).setContainsPawn(true);

        return new LogicResult(true);
    }

    public LogicResult setPawn(int x, int y) {
        LogicResult logicResult = validateCoordinate(x, y);

        if (!logicResult.isSuccess()) {
            return logicResult;
        }

        getTile(x, y).setContainsPawn(true);

        return logicResult;
    }


    /**
     * Checks whether the position on the {@link Board} contains a {@link Wall}.
     *
     * @return whether the wall is in the given position
     * @throws IllegalArgumentException if x or y is below 0
     */
    public boolean containsWall(int x, int y, boolean isHorizontal) {
        validateCoordinate(x, y);
        if (isHorizontal) {
            return horizontalWallData[x][y] != null;
        } else {
            return verticalWallData[x][y] != null;
        }
    }

    /**
     * Places a new {@link Wall} on to the {@link Board}.
     *
     * @param x            the x position
     * @param y            the y position
     * @param isHorizontal whether the wall is horizontal or not
     * @param isFirst      whether the wall is the first part or not
     * @param placedBy     who owns the wall
     * @throws IllegalArgumentException if x or y is below 0
     * @throws IllegalArgumentException if the player is null
     */
    public void setWall(int x, int y, boolean isHorizontal, boolean isFirst, Player placedBy) {
        validateCoordinateThrowException(x, y);

        if (placedBy == null) {
            throw new IllegalArgumentException("The player cannot be null");
        }

        if (isHorizontal) {
            horizontalWallData[x][y] = new WallData(x, y, isFirst, placedBy);
        } else {
            verticalWallData[x][y] = new WallData(x, y, isFirst, placedBy);
        }
    }

    /**
     * Returns a {@link Wall} given the x and y coordinate.
     *
     * @param x            the x coordinate
     * @param y            the y coordinate
     * @param isHorizontal whether the wall is horizontal
     * @return the wall
     * @throws IllegalArgumentException if x or y is below 0
     */
    public WallData getWall(int x, int y, boolean isHorizontal) {
        validateCoordinateThrowException(x, y);

        if (isHorizontal) {
            return horizontalWallData[x][y];
        } else {
            return verticalWallData[x][y];
        }
    }

    private void validateCoordinateThrowException(int x, int y) {
        LogicResult logicResult = validateCoordinate(x, y);

        if (!logicResult.isSuccess()) {
            throw new IllegalArgumentException(logicResult.getErrMsg());
        }
    }

    /**
     * Removes a {@link Wall} given the x and y coordinate.
     *
     * @param x            the x coordinate
     * @param y            the y coordinate
     * @param isHorizontal whether the wall is horizontal
     * @throws IllegalArgumentException if x or y is below 0
     */
    public void removeWall(int x, int y, boolean isHorizontal) {
        LogicResult isValid = validateCoordinate(x, y);

        if (!isValid.isSuccess()) {
            return;
        }

        if (isHorizontal) {
            horizontalWallData[x][y] = null;
        } else {
            verticalWallData[x][y] = null;
        }
    }

    /**
     * Gets the height of the board. Used to draw the board.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the board. Used to draw the board.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    private LogicResult validateCoordinate(int x, int y) {
        if ((x < 0 || y < 0) || (x >= width || y >= height)) {
            return new LogicResult(false, String.format("The coordinate (%d,%d) is outside the board", x, y));
        }
        return new LogicResult(true);
    }
}
