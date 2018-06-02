package quoridor.dao;

/**
 * Created by מאור סטודיו on 17/02/2018.
 */

public interface DAOFactory {

    <T extends DAO> T getDAO(String tableName);
}
