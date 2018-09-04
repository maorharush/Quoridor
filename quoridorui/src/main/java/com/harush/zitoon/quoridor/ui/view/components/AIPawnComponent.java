package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.Coordinate;
import com.harush.zitoon.quoridor.core.model.Pawn;

/**
 * Represents a Pawn within the main game screen.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 */

public class AIPawnComponent extends AbstractPawnComponent {

    public AIPawnComponent(int x, int y, String playerName, Pawn pawn) {
        super(x, y, playerName, pawn);
    }
}
