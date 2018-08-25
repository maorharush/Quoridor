package com.harush.zitoon.quoridor.core.dao.dbo;

import java.util.Objects;

//TODO All below changes must be changed in tables + DAO
public class GameRecDBO extends DBO {
    private int game_id;
    private int player_id; //TODO change to String and player_name
    private Character cur_col; // TODO Change to int and pawn_x
    private int cur_row; //TODO Change to int and pawn_y
    private int fence_col; //TODO Change to wall_x
    private int fence_row; //TODO Change to wall_y
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

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public Character getCur_col() {
        return cur_col;
    }

    public void setCur_col(Character cur_col) {
        this.cur_col = cur_col;
    }

    public void setCur_row(int cur_row) {
        this.cur_row = cur_row;
    }

    public int getCur_row() {
        return cur_row;
    }

    public int getFence_col() {
        return fence_col;
    }

    public void setFence_col(int fence_col) {
        this.fence_col = fence_col;
    }

    public int getFence_row() {
        return fence_row;
    }

    public void setFence_row(int fence_row) {
        this.fence_row = fence_row;
    }

    public void setCur_row(char cur_row) {
        this.cur_row = cur_row;
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
                player_id == that.player_id &&
                cur_row == that.cur_row &&
                fence_col == that.fence_col &&
                fence_row == that.fence_row &&
                Objects.equals(cur_col, that.cur_col) &&
                Objects.equals(fence_orien, that.fence_orien);
    }

    @Override
    public int hashCode() {

        return Objects.hash(game_id, player_id, cur_col, cur_row, fence_col, fence_row, fence_orien);
    }

    @Override
    public String toString() {
        return "GameRecDBO{" +
                "game_id=" + game_id +
                ", player_id=" + player_id +
                ", cur_col=" + cur_col +
                ", fence_col=" + fence_col +
                ", fence_row=" + fence_row +
                ", fence_orien=" + fence_orien +
                '}';
    }
}
