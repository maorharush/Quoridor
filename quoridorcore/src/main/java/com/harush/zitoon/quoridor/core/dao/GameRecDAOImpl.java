package com.harush.zitoon.quoridor.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.List;


public class GameRecDAOImpl extends BaseDAO implements GameRecDAO {

    public GameRecDAOImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        createTable();
    }

    @Override
    public void insert(List<GameRecDBO> dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

    @Override
    public List<GameRecDBO> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, (resultSet, i) -> {
            GameRecDBO gameRecDBO = new GameRecDBO();

            String cur_col = resultSet.getString("cur_col");
            String fence_orien = resultSet.getString("fence_orien");

            gameRecDBO.setGame_id(resultSet.getInt("game_id"));
            gameRecDBO.setPlayer_name(resultSet.getInt("player_name"));
            gameRecDBO.setPawn_x(resultSet.getInt("pawn_x"));
            gameRecDBO.setPawn_y(resultSet.getInt("pawn_y"));
            gameRecDBO.setWall_x(resultSet.getInt("wall_x"));
            gameRecDBO.setWall_y(resultSet.getInt("wall_y"));
            gameRecDBO.setFence_orien(fence_orien == null ? null : fence_orien.charAt(0));
            return gameRecDBO;
        });
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM " + TABLE_NAME);
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"game_recorder\" ( `game_id` INTEGER, `player_id` INTEGER, `cur_col` TEXT, `cur_row` INTEGER, `fence_col` TEXT, `fence_row` INTEGER, `fence_orien` TEXT, FOREIGN KEY(`game_id`) REFERENCES `games`(`game_id`) ON DELETE CASCADE, PRIMARY KEY(`game_id`) )");
    }
}
