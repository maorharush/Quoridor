package com.harush.zitoon.quoridor.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DAOFactoryImpl implements DAOFactory {

    private Map<String, DAO> daoMap;

    public DAOFactoryImpl() {
        daoMap = new HashMap<String, DAO>() {{
            put(GameRecDAO.TABLE_NAME, new GameRecDAOImpl());
            put(GameDAO.TABLE_NAME, new GameDAOImpl());
            put(PlayerDAO.TABLE_NAME, new PlayerDAOImpl());
        }};
    }

    @Override
    public DAO getDAO(String tableName) {
        return daoMap.get(tableName);
    }
}
