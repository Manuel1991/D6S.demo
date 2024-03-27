package com.D6.configclient.commons;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DBCPDataSource {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    //@Value("${datasource.username}")
    //private String dbUser;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    private static BasicDataSource ds;

    public DBCPDataSource() {
    }

    public Connection getConnection() throws SQLException {
        createDataSource(this.dbUrl, this.driverClassName);
        return ds.getConnection();
    }

    private void createDataSource(
            String dbUrl,
            String driverClassName
    ) {
        if (ds != null)
            return;

        ds = new BasicDataSource();

        ds.setUrl(dbUrl);
        ds.setDriverClassName(driverClassName);
        //ds.setUsername(dbUser);
        //ds.setPassword(dbPassword);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public BasicDataSource getDataSource() {
        return ds;
    }
}
