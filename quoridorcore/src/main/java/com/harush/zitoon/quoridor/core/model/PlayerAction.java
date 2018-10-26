package com.harush.zitoon.quoridor.core.model;

import java.util.Objects;

public class PlayerAction {

    private PlayerActionType playerActionType;
    private int x;
    private int y;
    private Boolean isHorizontal;
    private Boolean isFirst;
    private Player player;
    private PlayerAction parent;

    public PlayerAction(int x, int y, Player player) {
        this.player = player;
        this.playerActionType = PlayerActionType.MOVE_PAWN;
        this.x = x;
        this.y = y;
    }

    public PlayerAction(int x, int y, boolean isHorizontal, boolean isFirst, Player player) {
        this.player = player;
        this.playerActionType = PlayerActionType.PLACE_WALL;
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
        this.isFirst = isFirst;
    }

    public boolean isFirst() {
        return isFirst;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerAction that = (PlayerAction) o;
        return x == that.x &&
                y == that.y &&
                isHorizontal == that.isHorizontal &&
                isFirst == that.isFirst &&
                playerActionType == that.playerActionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerActionType, x, y, isHorizontal, isFirst);
    }

    @Override
    public String toString() {
        return "PlayerAction{" +
                "playerActionType=" + playerActionType +
                ", x=" + x +
                ", y=" + y +
                ", isHorizontal=" + isHorizontal +
                ", isFirst=" + isFirst +
                ", player=" + player +
                '}';
    }

    public PlayerAction getParent() {
        return parent;
    }

    public void setParent(PlayerAction parent) {
        this.parent = parent;
    }

    public int getPathLength() {
        if (parent == null) {
            return 1;
        }
        return parent.getPathLength() + 1;
    }
}
