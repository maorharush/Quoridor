package com.harush.zitoon.quoridor.core.dao.dbo;

import java.util.Objects;

public class PlayerDBO extends DBO {
    private Integer player_id;
    private String player_name;
    private String highest_score;
    private int is_AI;

    public Integer getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getHighest_score() {
        return highest_score;
    }

    public void setHighest_score(String highest_score) {
        this.highest_score = highest_score;
    }

    public int getIs_AI() {
        return is_AI;
    }

    public void setIs_AI(int is_AI) {
        this.is_AI = is_AI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerDBO)) return false;
        PlayerDBO playerDBO = (PlayerDBO) o;
        return is_AI == playerDBO.is_AI &&
                Objects.equals(player_id, playerDBO.player_id) &&
                Objects.equals(player_name, playerDBO.player_name) &&
                Objects.equals(highest_score, playerDBO.highest_score);
    }

    @Override
    public int hashCode() {

        return Objects.hash(player_id, player_name, highest_score, is_AI);
    }


    @Override
    public String toString() {
        return "PlayerDBO{" +
                "player_id='" + player_id + '\'' +
                ", player_name='" + player_name + '\'' +
                ", highest_score='" + highest_score + '\'' +
                ", is_AI=" + is_AI +
                '}';
    }
}
