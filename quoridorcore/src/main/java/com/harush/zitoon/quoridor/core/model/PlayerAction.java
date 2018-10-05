package com.harush.zitoon.quoridor.core.model;

public class PlayerAction {

    private PlayerActionType playerActionType;
    private int x;
    private int y;
    private Character wall_orien;
    private Player player;

    public PlayerAction(PlayerActionType playerActionType, int x, int y, Character wall_orien, Player player) {
        this.playerActionType = playerActionType;
        this.x = x;
        this.y = y;
        this.wall_orien = wall_orien;
        this.player = player;
    }

    public PlayerActionType getPlayerActionType() {
        return playerActionType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Character getWall_orien() {
        return wall_orien;
    }

    public Player getPlayer() {
        return player;
    }
}
