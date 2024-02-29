package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        //noinspection SqlWithoutWhere
        sqlHelper.getPreparedStatement("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                int executeUpdate = ps.executeUpdate();
                if (executeUpdate == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        final Resume resume = createResume(uuid);
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid=?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
            return null;
        });
        return resume;
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.getPreparedStatement("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumeMap = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    Resume resume = resumeMap.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, fullName);
                        resumeMap.put(uuid, resume);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resumeMap.get(rs.getString("resume_uuid")));
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resumeMap.get(rs.getString("resume_uuid")));
                }
            }
            return null;
        });
        return new ArrayList<>(resumeMap.values());
    }

    @Override
    public int size() {
        return sqlHelper.getPreparedStatement("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    private void saveSectionValue(PreparedStatement ps, Map.Entry<SectionType, Section> entry) throws SQLException {
        switch (entry.getValue()) {
            case TextSection textSection -> saveTextSection(ps, textSection);
            case ListSection listSection -> saveListSection(ps, listSection);
            case OrganizationSection organizationSection -> saveOrganizationSection(ps, organizationSection);
            default -> throw new IllegalStateException("Unexpected value: " + entry.getValue());
        }
    }

    private void saveTextSection(PreparedStatement ps, TextSection textSection) throws SQLException {
        String string = textSection.getTextSection();
        ps.setString(3, string);
    }

    private void saveListSection(PreparedStatement ps, ListSection listSection) throws SQLException {
        List<String> list = listSection.getListSections();
        String join = String.join("\n", list);
        ps.setString(3, join);
    }

    private void saveOrganizationSection(PreparedStatement ps, OrganizationSection organizationSection) throws SQLException {
        List<Organization> organizations = organizationSection.getOrganizations();
        List<String> org = new ArrayList<>();
        String join = null;
        for (Organization organization : organizations) {
            org.add(organization.getName());
            org.add(organization.getWebsite());
            List<Organization.Period> periods = organization.getPeriods();
            for (Organization.Period period : periods) {
                org.add(period.getName());
                org.add(period.getDescription());
                org.add(period.getStartDate().toString());
                org.add(period.getEndDate().toString());
            }
            join = String.join("\n", org);
        }
        System.out.println(join);
        ps.setString(3, join);
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            Map<SectionType, Section> sections = resume.getSections();
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                saveSectionValue(ps, entry);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            resume.addContact(type, value);
        }
    }

    private Resume createResume(String uuid) {
        return sqlHelper.getPreparedStatement("SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(rs.getString("uuid"), rs.getString("full_name"));
        });
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        Section section = getSection(rs, type);
        resume.addSection(type, section);
    }

    private Section getSection(ResultSet rs, SectionType type) throws SQLException {
        return switch (type) {
            case PERSONAL, OBJECTIVE -> getTextSection(rs);
            case ACHIEVEMENT, QUALIFICATIONS -> getListSection(rs);
            case EXPERIENCE, EDUCATION -> getOrgSection(rs);
        };
    }

    private TextSection getTextSection(ResultSet rs) throws SQLException {
        return new TextSection(rs.getString("value"));
    }

    private ListSection getListSection(ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String[] split = value.split("\n");
        List<String> list = new ArrayList<>(Arrays.asList(split));
        return new ListSection(list);
    }

    private OrganizationSection getOrgSection(ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String[] split = value.split("\n");
        List<Organization> organizations = new ArrayList<>();
        List<Organization.Period> periods = new ArrayList<>();
        for (String string : split) {
//            Organization organization = new Organization();
//            organizations.add(organization.setName(string));
//            organization.setWebsite(string);
//            Organization.Period period = new Organization.Period();
//            period.setName(string);
//            period.setDescription(string);
//            period.setStartDate(LocalDate.parse(string));
//            period.setEndDate(LocalDate.parse(string));
        }
        return null;
    }
}
