package com.harush.zitoon.quoridor.core.dao;

/**
 *
 */

public interface DAOFactory {

    <T extends DAO> T getDAO(String tableName);
}
