package com.ivannikov.webapp.util;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.sql.ConnectionFactory;
import com.ivannikov.webapp.sql.SqlExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory factory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T getPreparedStatement(String sql, SqlExecutor<T> executor) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
