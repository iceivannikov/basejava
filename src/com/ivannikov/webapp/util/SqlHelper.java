package com.ivannikov.webapp.util;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.sql.ConnectionFactory;
import com.ivannikov.webapp.sql.SqlExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static <T> T getPreparedStatement(ConnectionFactory factory, String sql, SqlExecutor<T> executor) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
