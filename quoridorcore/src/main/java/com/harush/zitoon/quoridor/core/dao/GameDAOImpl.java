package com.harush.zitoon.quoridor.core.dao;

import com.harush.zitoon.quoridor.core.dao.dbo.GameDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.SQLException;
import java.util.List;

/**
 * Data access object for a game instance.
 */
public class GameDAOImpl extends BaseDAO implements GameDAO {

    public GameDAOImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
        createTable();
    }

    /**
     * querying up from db for the all game's records.
     * @return List of gameDBO.
     */
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

    /**
     * inserts a game instance record.
     * @param dbos, a gameDBO objects. can be singular or listed.
     */
    @Override
    public void insert(GameDBO... dbos) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(TABLE_NAME);
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dbos);
        simpleJdbcInsert.executeBatch(batch);
    }
    /**
     * ??
     * @param dbos
     */
    @Override
    public void insert(List<GameDBO> dbos) {
        insert(dbos.toArray(new GameDBO[]{}));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute("DELETE FROM " + TABLE_NAME);
    }

    private void createTable() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `games` (\n" +
                "\t`game_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`winner`\tINTEGER,\n" +
                "\t`num_of_moves`\tINTEGER,\n" +
                "\t`start_date`\tNUMERIC,\n" +
                "\t`end_date`\tNUMERIC,\n" +
                "\tFOREIGN KEY(`winner`) REFERENCES `players`(`player_id`)\n" +
                ");");
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
