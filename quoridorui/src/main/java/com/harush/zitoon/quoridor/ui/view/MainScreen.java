package com.harush.zitoon.quoridor.ui.view;

import com.harush.zitoon.quoridor.ui.view.components.PawnComponent;

public interface MainScreen {

    int toBoard(double pixel);

    boolean isCurrentTurn(PawnComponent.PawnType type);

    void checkForWinnerAndUpdateTurn(PawnComponent.PawnType type, int newX, int newY);
}
