package com.harush.zitoon.quoridor.runner;

import com.harush.zitoon.quoridor.common.model.BoardColumn;
import com.harush.zitoon.quoridor.common.model.BoardCoordinate;
import com.harush.zitoon.quoridor.common.model.BoardRow;
import com.harush.zitoon.quoridor.core.Pawn;
import com.harush.zitoon.quoridor.ui.GameUI;

public class GameRunner {

    public static void main(String[] args) {

        GameUI gameUI = new GameUI(new Pawn());
        gameUI.movePawnOnScreen(new BoardCoordinate(BoardColumn.B, BoardRow._1));
    }
}
