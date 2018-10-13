package com.harush.zitoon.quoridor.ui.view.utils;

import com.harush.zitoon.quoridor.core.model.Utils.Settings;

public final class UIUtils {

    private UIUtils() {}

    public static final int TILE_SIZE = Settings.getSingleton().getTileSize();


    /**
     * Converts a pixel on the board to a coordinate.
     *
     * @param pixel the pixel
     * @return a coordinate
     */
    public static int toBoardCoordinate(double pixel) {
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }
}
