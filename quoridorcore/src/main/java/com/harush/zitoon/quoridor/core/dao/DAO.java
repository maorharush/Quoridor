package com.harush.zitoon.quoridor.core.dao;

import java.util.List;

import com.harush.zitoon.quoridor.core.dao.dbo.DBO;
import com.harush.zitoon.quoridor.core.dao.dbo.PlayerDBO;


public interface DAO<T extends DBO> {

    void insert(T... dbos);

    void insert(List<T> dbos);

    //int update(List<T> dbos);

    //int delete(List<T> dbos);

    List<T> getAll();

    void deleteAll();
}
