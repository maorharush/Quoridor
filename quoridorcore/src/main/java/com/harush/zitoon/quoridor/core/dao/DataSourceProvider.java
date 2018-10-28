package com.harush.zitoon.quoridor.core.dao;

import javax.sql.DataSource;
import org.sqlite.*;
import com.mchange.v2.c3p0.*;

import java.sql.SQLException;

/**
 * config's the database parameters for sqlite to use.
 */
public class DataSourceProvider {

    public static DataSource getDataSource() {
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
        unpooled.setDatabaseName("quoridorDB");
        unpooled.setUrl(unpooled.getUrl() + ":resource:db/quoridorDB.db");

        // get a pooled c3p0 DataSource that wraps the unpooled SQLite DataSource
        try {
            return DataSources.pooledDataSource( unpooled );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
