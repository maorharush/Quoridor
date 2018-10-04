package com.harush.zitoon.quoridor.core.dao;

import java.util.List;

import com.harush.zitoon.quoridor.core.dao.dbo.DBO;


public interface DAO<T extends DBO> {

    void insert(List<T> dbos);

    //int update(List<T> dbos);

    //int delete(List<T> dbos);

    <ID> List<T> getRows(String columnName, ID id);

    List<T> getAll();

    void deleteAll();
}
