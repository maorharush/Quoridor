package quoridor.dao;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import quoridor.dao.dbo.GameDBO;


import java.util.List;

import static quoridor.dao.table.GameRecTable.TABLE_NAME;

public class GameDAOImpl extends BaseDAO implements GameDAO{
    public GameDAOImpl(){

    }

    @Override
    public List<GameDBO> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + TABLE_NAME, (resultSet, i) -> {
            GameDBO gameDBO = new GameDBO();


            gameDBO.setGameID(resultSet.getString("gameID"));
            gameDBO.setWinner(resultSet.getInt("winner"));
            gameDBO.setNumOfMoves(resultSet.getInt("numOfMoves"));
            gameDBO.setDateStart(resultSet.getInt("dateStart"));
            gameDBO.setDateEnd(resultSet.getInt("dateEnd"));


            return gameDBO;
        });
    }

    @Override
    public void insert(List<GameDBO> dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }

}
