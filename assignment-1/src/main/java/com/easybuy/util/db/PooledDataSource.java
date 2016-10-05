package com.easybuy.util.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Data source back-ended by commons-dbcp which provides a database connection pool.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public class PooledDataSource {

    private static String DBCP_PROPERTIES_FILENAME = "dbcp.properties";
    private static Logger log = LoggerFactory.getLogger(PooledDataSource.class);
    private static BasicDataSource ds;


    public static DataSource getDataSource() {
        if (null == ds) {
            Properties p = null;
            try {
                p = new Properties();
                p.load(PooledDataSource.class.getClassLoader().getResourceAsStream(DBCP_PROPERTIES_FILENAME));
                ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
                log.warn("Datasource created (driver='{}', url='{}').", ds.getDriverClassName(), ds.getUrl());
            } catch (Exception e) {
                log.error("Fatal error occurred while initializing data source to database '{}'. Error message: {}",
                        p.get("url"), e.getLocalizedMessage());
            }
        }
        return ds;
    }


    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }


    public static synchronized void close() {
        if (null != ds) {
            try {
                ds.close();
                log.warn("Datasource closed.");
            } catch (SQLException e) {
                log.error("Error occurred while closing datasource: {}", e.getLocalizedMessage());
            }
        }
    }


    public static synchronized void shutdown() {
        /**
         * close data source
         */
        try {
            ds.close();
            log.warn("Datasource closed.");
        } catch (SQLException e) {
            log.error("Error occurred while closing datasource: {}", e.getLocalizedMessage());
        }

        /**
         * also deregister all the jdbc drivers loaded by pool
         */
        String url = ds.getUrl();
        try {
            Driver drv = DriverManager.getDriver(url);
            DriverManager.deregisterDriver(drv);
            log.warn("JDBC driver '{}' (url:'{}') deregistered.", drv.getClass().getName(), url);
        } catch (SQLException e) {
            log.error("Error occurred while deregistering JDBC driver: '{}', error: {}", url,
                    e.getLocalizedMessage());
        }
    }
}
