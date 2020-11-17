package com.ascending.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtil {
    private static JDBCUtil jdbcUtil = new JDBCUtil();

    private JDBCUtil() {};

    public static JDBCUtil getJdbcUtil() {
        return jdbcUtil;
    }

    private static Connection JDBCFactory() {
        String DB_URL = System.getProperty("database.url");
        String USER = System.getProperty("database.user");
        String PASS = System.getProperty("database.password");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {
        return JDBCFactory();
    }
}
