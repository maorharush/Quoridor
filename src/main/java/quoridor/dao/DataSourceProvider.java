package quoridor.dao;

import javax.sql.DataSource;
import org.sqlite.*;
import com.mchange.v2.c3p0.*;

import java.sql.SQLException;

public class DataSourceProvider {

    public static DataSource getDataSource() throws SQLException {
        // configure SQLite
        SQLiteConfig config = new org.sqlite.SQLiteConfig();
        config.setReadOnly(false);
        config.setPageSize(4096); //in bytes
        config.setCacheSize(2000); //number of pages
        config.setOpenMode(SQLiteOpenMode.CREATE);
        config.setSynchronous(SQLiteConfig.SynchronousMode.OFF);
        config.setJournalMode(SQLiteConfig.JournalMode.OFF);

        // get an unpooled SQLite DataSource with the desired configuration
        SQLiteDataSource unpooled = new SQLiteDataSource( config );

        // get a pooled c3p0 DataSource that wraps the unpooled SQLite DataSource
        return DataSources.pooledDataSource( unpooled );
    }
}
