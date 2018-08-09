package com.harush.zitoon.quoridor.core.dao.dbo;

import java.util.Objects;

public class GameDBO extends DBO {
    private String game_id;
    private int winner;
    private int num_of_moves;
    private long start_date;
    private long end_date;

    public GameDBO() {
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getNum_of_moves() {
        return num_of_moves;
    }

    public void setNum_of_moves(int num_of_moves) {
        this.num_of_moves = num_of_moves;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDBO gameDBO = (GameDBO) o;
        return winner == gameDBO.winner &&
                num_of_moves == gameDBO.num_of_moves &&
                start_date == gameDBO.start_date &&
                end_date == gameDBO.end_date &&
                Objects.equals(game_id, gameDBO.game_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(game_id, winner, num_of_moves, start_date, end_date);
    }

    @Override
    public String toString() {
        return "GameDBO{" +
                "game_id='" + game_id + '\'' +
                ", winner=" + winner +
                ", num_of_moves=" + num_of_moves +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
