package quoridor.dao;

import java.util.List;

import quoridor.dao.dbo.DBO;


public interface DAO<T extends DBO> {

    void insert(List<T> dbos);

    //int update(List<T> dbos);

    //int delete(List<T> dbos);

    List<T> getAll();
}
