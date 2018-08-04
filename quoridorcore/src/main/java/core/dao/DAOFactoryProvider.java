package core.dao;

import java.sql.SQLException;

public abstract class DAOFactoryProvider {

    public static DAOFactory createDAOFactory() throws SQLException, ClassNotFoundException {
        return new DAOFactoryImpl();
    }
}
