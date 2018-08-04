package com.harush.zitoon.quoridor.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;


public abstract class BaseDAO {

    protected JdbcTemplate jdbcTemplate;
    private static boolean isCreated = false;

    public BaseDAO() {
        try {

            if (!isCreated) {
                Class.forName("org.sqlite.JDBC");
                jdbcTemplate = new JdbcTemplate(DataSourceProvider.getDataSource());
                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"game_recorder\" ( `game_id` INTEGER, `player_id` INTEGER, `cur_col` TEXT, `cur_row` INTEGER, `fence_col` TEXT, `fence_row` INTEGER, `fence_orien` TEXT, FOREIGN KEY(`game_id`) REFERENCES `games`(`game_id`) ON DELETE CASCADE, PRIMARY KEY(`game_id`) )");
                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS \"games\" ( `game_id` INTEGER PRIMARY KEY AUTOINCREMENT, `winner` INTEGER, `num_moves` INTEGER, `date_start` NUMERIC, `date_end` NUMERIC )");
                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS `players` ( `player_id` INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, `player_name` TEXT, `highest_score` INTEGER, `isAI` INTEGER )");
                isCreated = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to start SQLite DB connection", e);
        }
    }

}
