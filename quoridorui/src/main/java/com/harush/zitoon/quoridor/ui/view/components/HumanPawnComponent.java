package com.harush.zitoon.quoridor.ui.view.components;

import com.harush.zitoon.quoridor.core.model.Coordinate;
import com.harush.zitoon.quoridor.core.model.Pawn;
import com.harush.zitoon.quoridor.ui.view.utils.UIUtils;

/**
 * Represents a Pawn within the main game screen.
 * Adapted from <a href="https://github.com/AlmasB/FXTutorials/blob/master/src/com/almasb/checkers/Piece.java">AlmasB</a>.
 */

public class HumanPawnComponent extends AbstractPawnComponent {

    private double mouseX, mouseY;

    public HumanPawnComponent(int x, int y, String playerName, Pawn pawn) {
        super(x, y, playerName, pawn);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> relocate(e.getSceneX() - mouseX + currentX, e.getSceneY() - mouseY + currentY));

        setOnMouseReleased(e -> {
            int newX = UIUtils.toBoardCoordinate(getLayoutX());
            int newY = UIUtils.toBoardCoordinate(getLayoutY());

            move(newX, newY);
        });
    }
}
