package com.harush.zitoon.quoridor.core.model;

import com.rits.cloning.Cloner;

public class pathClearanceImpl implements pathClearance {

    public boolean opponentPathIsClear(GameSession gameSession) {
        Cloner clone = new Cloner();
        Board copyOfBoard = clone.deepClone(gameSession.getBoard());



        return  true;
    }
}
