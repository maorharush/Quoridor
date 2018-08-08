package com.harush.zitoon.quoridor.ui;

import com.harush.zitoon.quoridor.core.logic.*;


public class GameRunner {

    public static void main(String[] args) {
        BoardBoxLogic[][] boardBoxes = new BoardBoxLogic[9][9];
        Board board = new BoardLogic(boardBoxes);

        GameUI gameUI = new GameUI(board, new PawnLogic());
        gameUI.movePawnOnScreen(-1, -1);
    }
}
