package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.sql.ConnectionFactory;
import com.ivannikov.webapp.sql.SqlExecutor;
import com.ivannikov.webapp.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final ConnectionFactory factory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        factory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        //noinspection SqlWithoutWhere
        String sql = "DELETE FROM resume";
        SqlHelper.getPreparedStatement(factory, sql, (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        SqlExecutor<Void> executor = ps -> {
            if (resumeExists(resume.getUuid())) {
                throw new ExistStorageException(resume.getUuid());
            }
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        };
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    @Override
    public void update(Resume resume) {
        SqlExecutor<Void> executor = ps -> {
            if (!resumeExists(resume.getUuid())) {
                throw new NotExistStorageException(resume.getUuid());
            }
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.executeUpdate();
            return null;
        };
        String sql = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    @Override
    public Resume get(String uuid) {
        SqlExecutor<Resume> executor = ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        };
        String sql = "SELECT * FROM resume WHERE uuid=?";
        return SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    @Override
    public void delete(String uuid) {
        SqlExecutor<Void> executor = ps -> {
            if (!resumeExists(uuid)) {
                throw new NotExistStorageException(uuid);
            }
            ps.setString(1, uuid);
            ps.execute();
            return null;
        };
        String sql = "DELETE FROM resume WHERE uuid=?";
        SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlExecutor<List<Resume>> executor = ps -> {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumes.add(resume);
            }
            return resumes;
        };
        String sql = "SELECT uuid, full_name FROM resume";
        return SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    @Override
    public int size() {
        final int[] size = {0};
        SqlExecutor<Integer> executor = ps -> {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                size[0] = resultSet.getInt(1);
            }
            return size[0];
        };
        String sql = "SELECT COUNT(*) FROM resume";
        return SqlHelper.getPreparedStatement(factory, sql, executor);
    }

    private boolean resumeExists(String uuid) {
        SqlExecutor<Boolean> executor = ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            return rowCount > 0;
        };
        String sql = "SELECT COUNT(*) FROM resume WHERE uuid = ?";
        return SqlHelper.getPreparedStatement(factory, sql, executor);
    }
}
