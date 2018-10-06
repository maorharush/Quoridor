package com.harush.zitoon.quoridor.core.dao.dbo;

import java.util.Objects;

public class GameRecDBO extends DBO {
    private int game_id;
    private String player_name;
    private int pawn_x;
    private int pawn_y;
    private int wall_x;
    private int wall_y;
    private Character fence_orien;

    public boolean setGame_id(int game_id) {
        if (game_id < 0) {
            return false;
        }

        this.game_id = game_id;

        return true;
    }

    public int getGame_id() {
        return game_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getPawn_x() {
        return pawn_x;
    }

    public void setPawn_x(int pawn_x) {
        this.pawn_x = pawn_x;
    }

    public void setPawn_y(int pawn_y) {
        this.pawn_y = pawn_y;
    }

    public int getPawn_y() {
        return pawn_y;
    }

    public int getWall_x() {
        return wall_x;
    }

    public void setWall_x(int wall_x) {
        this.wall_x = wall_x;
    }

    public int getWall_y() {
        return wall_y;
    }

    public void setWall_y(int wall_y) {
        this.wall_y = wall_y;
    }

    public Character getFence_orien() {
        return fence_orien;
    }

    public void setFence_orien(Character fence_orien) {
        this.fence_orien = fence_orien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRecDBO that = (GameRecDBO) o;
        return game_id == that.game_id &&
                pawn_x == that.pawn_x &&
                pawn_y == that.pawn_y &&
                wall_x == that.wall_x &&
                wall_y == that.wall_y &&
                Objects.equals(player_name, that.player_name) &&
                Objects.equals(fence_orien, that.fence_orien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game_id, player_name, pawn_x, pawn_y, wall_x, wall_y, fence_orien);
    }

    @Override
    public String toString() {
        return "GameRecDBO{" +
                "game_id=" + game_id +
                ", player_name=" + player_name +
                ", pawn_x=" + pawn_x +
                ", wall_x=" + wall_x +
                ", wall_y=" + wall_y +
                ", fence_orien=" + fence_orien +
                '}';
    }
}
