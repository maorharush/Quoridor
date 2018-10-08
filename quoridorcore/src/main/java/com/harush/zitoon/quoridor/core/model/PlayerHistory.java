package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.List;

/**
 * holds up the records of a game, by players.
 */
public class PlayerHistory {

    private Player player;
    private List<GameRecDBO> gameRecords;

    public PlayerHistory(Player player, List<GameRecDBO> gameRecords) {
        this.player = player;
        this.gameRecords = gameRecords;
    }

    public Player getPlayer() {
        return player;
    }

    public List<GameRecDBO> getGameRecords() {
        return gameRecords;
    }
}
