package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.SQLException;
import java.util.List;

public class GameDAOImpl extends BaseDAO implements GameDAO {

    public GameDAOImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        createTable();
    }

    @Override
    public List<GameDBO> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, (resultSet, i) -> {
            GameDBO gameDBO = new GameDBO();
            gameDBO.setGame_id(resultSet.getInt("game_id"));
            gameDBO.setWinner(resultSet.getInt("winner"));
            gameDBO.setNum_of_moves(resultSet.getInt("num_of_moves"));
            gameDBO.setStart_date(resultSet.getLong("start_date"));
            gameDBO.setEnd_date(resultSet.getLong("end_date"));
            return gameDBO;
        });
    }

    @Override
    public void insert(GameDBO... dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

    @Override
    public void insert(List<GameDBO> dbos) {
        insert(dbos.toArray(new GameDBO[]{}));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM " + TABLE_NAME);
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"games\" ( `game_id` INTEGER PRIMARY KEY AUTOINCREMENT, `winner` INTEGER, `num_of_moves` INTEGER, `start_date` NUMERIC, `end_date` NUMERIC )");
    }

    @Override
    public int getLastGameID() {
        Integer maxID = jdbcTemplate.<Integer>queryForObject("SELECT MAX(game_id) FROM " + TABLE_NAME, Integer.class);
        if (maxID == null) {
            return -1;
        }
        return maxID;
    }

    @Override
    public void updateGameRecord(GameDBO gameDBO) {
        //TODO MorManush: Implement
    }
}
