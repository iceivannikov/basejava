package com.ivannikov.webapp.sql;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.StorageException;

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
        return "23505".equals(e.getSQLState());
    }
}
