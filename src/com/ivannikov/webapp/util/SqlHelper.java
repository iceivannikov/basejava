package com.ivannikov.webapp.util;

import com.ivannikov.webapp.exception.ExistStorageException;
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

    @SuppressWarnings("SqlSourceToSinkFlow")
    public <T> T getPreparedStatement(String sql, SqlExecutor<T> executor) {
        //noinspection TryStatementWithMultipleResources
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if (isDuplicateKeyError(e)) {
                throw new ExistStorageException("Duplicate primary key error");
            } else {
                throw new StorageException(e);
            }
        }
    }

    private boolean isDuplicateKeyError(SQLException e) {
        return e.getSQLState().startsWith("23") && e.getMessage().contains("duplicate key");
    }
}
