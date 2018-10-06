package com.harush.zitoon.quoridor.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class GameRecDAOImpl extends BaseDAO implements GameRecDAO {


    public GameRecDAOImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        createTable();
    }

    @Override
    public void insert(GameRecDBO... dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

    @Override
    public void insert(List<GameRecDBO> dbos) {
        insert(dbos.toArray(new GameRecDBO[]{}));
    }

    @Override
    public List<GameRecDBO> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, (resultSet, i) -> {
            return getGameRecDBO(resultSet);
        });
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM " + TABLE_NAME);
    }

    @Override
    public int getMaxID() {
        Integer maxID = jdbcTemplate.<Integer>queryForObject("SELECT MAX(game_id) FROM " + TABLE_NAME, Integer.class);
        if (maxID == null) {
            return -1;
        }
        return maxID;
    }

    @Override
    public List<GameRecDBO> getGameRecords(int gameId) {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE game_id='" + gameId + "'", (resultSet, i) -> getGameRecDBO(resultSet));

    }

    @Override
    public List<GameRecDBO> getPlayerRecords(int gameId, String playerName) {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME + " WHERE game_id='" + gameId + "' AND player_name='" + playerName + "'", (resultSet, i) -> getGameRecDBO(resultSet));
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `game_recorder` (\n" +
                "\t`game_rec_id`\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t`game_id`\tINTEGER,\n" +
                "\t`player_name`\tINTEGER,\n" +
                "\t`pawn_x`\tINTEGER,\n" +
                "\t`pawn_y`\tINTEGER,\n" +
                "\t`wall_x`\tINTEGER,\n" +
                "\t`wall_y`\tINTEGER,\n" +
                "\t`fence_orien`\tTEXT,\n" +
                "\tFOREIGN KEY(`game_id`) REFERENCES `game_recorder`\n" +
                ");");
    }

    private GameRecDBO getGameRecDBO(ResultSet resultSet) throws SQLException {
        GameRecDBO gameRecDBO = new GameRecDBO();
        String fence_orien = resultSet.getString("fence_orien");
        gameRecDBO.setGame_id(resultSet.getInt("game_id"));
        gameRecDBO.setPlayer_name(resultSet.getString("player_name"));
        gameRecDBO.setPawn_x(resultSet.getInt("pawn_x"));
        gameRecDBO.setPawn_y(resultSet.getInt("pawn_y"));
        gameRecDBO.setWall_x(resultSet.getInt("wall_x"));
        gameRecDBO.setWall_y(resultSet.getInt("wall_y"));
        gameRecDBO.setFence_orien(fence_orien == null ? null : fence_orien.charAt(0));
        return gameRecDBO;
    }

}
