package quoridor.dao;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import quoridor.dao.table.GameRecTable;


public class DAOFactoryImpl implements DAOFactory {

    private Map<String, DAO> daoMap;

    public DAOFactoryImpl() throws SQLException, ClassNotFoundException {
        daoMap = new HashMap<String, DAO>() {{
            put(GameRecTable.TABLE_NAME, new GameRecDAOImpl());
        }};
    }

    @Override
    public DAO getDAO(String tableName) {
        return daoMap.get(tableName);
    }
}
