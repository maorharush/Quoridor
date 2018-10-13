package com.harush.zitoon.quoridor.core.model.Utils;

import com.harush.zitoon.quoridor.core.model.Coordinate;
import com.harush.zitoon.quoridor.core.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * core component with the authorities to decide on winner.
 * by game rules, a winner is a player which was able to get its pawn to the opposite side of its starting location.
 */
public class WinnerDeciderLogic implements WinnerDecider {

    private static final int BOARD_WIDTH = Settings.getSingleton().getBoardWidth() - 1;

    private static final int BOARD_HEIGHT = Settings.getSingleton().getBoardHeight() - 1;

    /**
     * a map with the four starting location for players.
     */
    private static final Map<Coordinate, List<Coordinate>> initialCoordinate2WinningRowMap = new HashMap<Coordinate, List<Coordinate>>() {{
        put(new Coordinate(0, BOARD_HEIGHT / 2), getVerticalRow(BOARD_WIDTH));      // Pawn started on the left
        put(new Coordinate(BOARD_WIDTH, BOARD_HEIGHT / 2), getVerticalRow(0));      // Pawn started on the right
        put (new Coordinate(BOARD_WIDTH / 2, BOARD_HEIGHT), getHorizontalRow(0));   // Pawn started on the bottom
        put(new Coordinate(BOARD_WIDTH / 2, 0), getHorizontalRow(BOARD_HEIGHT));    // Pawn started on the top
    }};

    /**
     * comparing between starting location coordinate, and the current.
     * @param player
     * @return Boolean, if true, the player is the winner.
     */
    @Override
    public boolean isWinner(Player player) {
        Coordinate initialCoordinate = player.getPawn().getInitialCoordinate();
        Coordinate currentCoordinate = player.getPawn().getCurrentCoordinate();

        List<Coordinate> winningRow = initialCoordinate2WinningRowMap.get(initialCoordinate);
        return winningRow.contains(currentCoordinate);
    }

    private static List<Coordinate> getVerticalRow(int x) {
        List<Coordinate> verticalRow = new ArrayList<>();
        for (int y = 0; y < BOARD_HEIGHT + 1; y++) {
            Coordinate coordinate = new Coordinate(x, y);
            verticalRow.add(coordinate);
        }
        return verticalRow;
    }

    private static List<Coordinate> getHorizontalRow(int y) {
        List<Coordinate> horizontalRow = new ArrayList<>();
        for (int x = 0; x < BOARD_WIDTH + 1; x++) {
            Coordinate coordinate = new Coordinate(x, y);
            horizontalRow.add(coordinate);
        }
        return horizontalRow;
    }
}
