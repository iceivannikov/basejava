package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.sql.ConnectionFactory;

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
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            if (resumeExists(connection, resume.getUuid())) {
                throw new ExistStorageException(resume.getUuid());
            }
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            if (!resumeExists(connection, resume.getUuid())) {
                throw new NotExistStorageException(resume.getUuid());
            }
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid=?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
            if (!resumeExists(connection, uuid)) {
                throw new NotExistStorageException(uuid);
            }
            ps.setString(1, uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT uuid, full_name FROM resume")) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumes.add(resume);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection connection = factory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM resume")) {
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                size = resultSet.getInt(1);
            }
            return size;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    private boolean resumeExists(Connection connection, String uuid) throws SQLException {
        try (PreparedStatement selectStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM resume WHERE uuid = ?")) {
            selectStatement.setString(1, uuid);
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            return rowCount > 0;
        }
    }
}
