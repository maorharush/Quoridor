package com.harush.zitoon.quoridor.core.dao;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

import java.util.List;

import static com.harush.zitoon.quoridor.core.dao.table.GameRecTable.TABLE_NAME;

public class GameRecDAOImpl extends BaseDAO implements GameRecDAO {

    public GameRecDAOImpl() {

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
            gameRecDBO.setPlayer_id(resultSet.getInt("player_id"));
            gameRecDBO.setCur_col(cur_col == null ? null : cur_col.charAt(0));
            gameRecDBO.setCur_row(resultSet.getInt("cur_row"));
            gameRecDBO.setFence_col(resultSet.getInt("fence_col"));
            gameRecDBO.setFence_row(resultSet.getInt("fence_row"));
            gameRecDBO.setFence_orien(fence_orien == null ? null : fence_orien.charAt(0));
            return gameRecDBO;
        });
    }
}
