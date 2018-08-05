package com.harush.zitoon.quoridor.ui;

import com.harush.zitoon.quoridor.core.logic.Pawn;
import com.harush.zitoon.quoridor.core.model.BoardColumn;
import com.harush.zitoon.quoridor.core.model.BoardCoordinate;
import com.harush.zitoon.quoridor.core.model.BoardRow;

public class GameRunner {

    public static void main(String[] args) {
        GameUI gameUI = new GameUI(new Pawn());
        gameUI.movePawnOnScreen(new BoardCoordinate(BoardColumn.B, BoardRow._1));
    }
}
