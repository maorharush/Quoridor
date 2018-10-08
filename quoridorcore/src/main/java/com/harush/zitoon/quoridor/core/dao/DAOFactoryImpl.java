package com.harush.zitoon.quoridor.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for DAOs - Holds the different DOAs and enables to get them by their corresponding table name
 */
public class DAOFactoryImpl implements DAOFactory {

    private Map<String, DAO> daoMap;

    private static DAOFactoryImpl instance;

    private DAOFactoryImpl() {
        daoMap = new HashMap<>();
        daoMap.put(GameRecDAO.TABLE_NAME, new GameRecDAOImpl());
        daoMap.put(GameDAO.TABLE_NAME, new GameDAOImpl());
        daoMap.put(PlayerDAO.TABLE_NAME, new PlayerDAOImpl());
    }

    public static DAOFactory instance() {
        if (instance == null) {
            instance = new DAOFactoryImpl();
        }
        return instance;
    }

    @Override
    public DAO getDAO(String tableName) {
        return daoMap.get(tableName);
    }
}
