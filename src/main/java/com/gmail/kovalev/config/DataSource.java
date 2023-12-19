package com.gmail.kovalev.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(Config.getInstance().config.get("db").get("url"));
        config.setUsername(Config.getInstance().config.get("db").get("login"));
        config.setPassword(Config.getInstance().config.get("db").get("password"));

        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
