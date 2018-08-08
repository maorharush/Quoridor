package com.harush.zitoon.quoridor.core.dao;


import com.harush.zitoon.quoridor.core.dao.table.GameRecTable;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;

import com.harush.zitoon.quoridor.core.dao.table.PlayerTable;

import java.sql.ResultSet;
import java.util.List;

public class PlayerDAOImpl extends BaseDAO implements PlayerDAO{
    public PlayerDAOImpl(){

    }
    @Override
    public  List<PlayerDBO> getAll(){
        return jdbcTemplate.query("SELECT * FROM " + PlayerTable.TABLE_NAME, (ResultSet resultSet, int i) -> {
            PlayerDBO playerDBO = new PlayerDBO();


            playerDBO.setPlayerID(resultSet.getString("playerID"));
            playerDBO.setPlayerName(resultSet.getString("playerName"));
            playerDBO.setHighestScore(resultSet.getString("HighestScore"));
            playerDBO.setIsAI(resultSet.getInt("isAI"));

            return playerDBO;
        });
    }
    @Override
    public void insert(List<PlayerDBO> dbos){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(GameRecTable.TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

}
