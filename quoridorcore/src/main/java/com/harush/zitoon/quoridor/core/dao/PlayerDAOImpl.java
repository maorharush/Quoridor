package com.harush.zitoon.quoridor.core.dao;


import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerDAOImpl extends BaseDAO implements PlayerDAO {

    public PlayerDAOImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        createTable();
    }

    @Override
    public List<PlayerDBO> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, (ResultSet resultSet, int i) -> {
            PlayerDBO playerDBO = new PlayerDBO();
            playerDBO.setPlayer_id(resultSet.getInt("player_id"));
            playerDBO.setPlayer_name(resultSet.getString("player_name"));
            playerDBO.setHighest_score(resultSet.getString("highest_score"));
            playerDBO.setIs_AI(resultSet.getInt("is_AI"));
            return playerDBO;
        });
    }

    @Override
    public void insert(PlayerDBO... dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

    @Override
    public void insert(List<PlayerDBO> dbos) {
        insert(dbos.toArray(new PlayerDBO[]{}));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM " + TABLE_NAME);
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"players\" ( `player_id` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, `player_name` TEXT, `highest_score` INTEGER, `is_AI` INTEGER )");
    }
}
