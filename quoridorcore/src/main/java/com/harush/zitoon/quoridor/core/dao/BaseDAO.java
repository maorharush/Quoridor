package com.harush.zitoon.quoridor.core.dao;

import org.springframework.jdbc.core.JdbcTemplate;


public abstract class BaseDAO {

    protected JdbcTemplate jdbcTemplate;
    private static boolean isCreated = false;

    public BaseDAO() {
        try {

            if (!isCreated) {
                Class.forName("org.sqlite.JDBC");
                isCreated = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to start SQLite DB connection", e);
        }
    }

}
