package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataOutputSerializationStrategy implements Strategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            serializeContact(dos, contacts);
            Map<SectionType, Section> sections = resume.getSections();
            serializeSection(dos, sections);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            deserializeContacts(dis, resume);
            deserializeSections(dis, resume);
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection,
                                        DataOutputStream dos,
                                        StorageConsumer<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            action.accept(item);
        }
    }

    private void serializeContact(DataOutputStream dos, Map<ContactType, String> contacts) throws IOException {
        writeWithException(contacts.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private void serializeSection(DataOutputStream dos, Map<SectionType, Section> sections) throws IOException {
        writeWithException(sections.entrySet(), dos, entry -> {
            dos.writeUTF(entry.getKey().name());
            serializeSection(entry.getValue(), dos);
        });
    }

    private void serializeSection(Section section, DataOutputStream dos) throws IOException {
        switch (section) {
            case TextSection textSection -> serializeTextSection(textSection, dos);
            case ListSection listSection -> serializeListSection(listSection, dos);
            case OrganizationSection organizationSection -> serializeOrganization(organizationSection, dos);
            case null, default -> throw new StorageException("Unexpected value: " + section);
        }
    }

    private void serializeTextSection(TextSection section, DataOutputStream dos) throws IOException {
        dos.writeUTF(section.getTextSection());
    }

    private void serializeListSection(ListSection section, DataOutputStream dos) throws IOException {
        writeWithException(section.getListSections(), dos, dos::writeUTF);
    }

    private void serializeOrganization(OrganizationSection section, DataOutputStream dos) throws IOException {
        writeWithException(section.getOrganizations(), dos, organization -> {
            dos.writeUTF(organization.getName());
            dos.writeUTF(organization.getWebsite());
            writeWithException(organization.getPeriods(), dos, period -> {
                dos.writeUTF(period.getName());
                dos.writeUTF(period.getDescription());
                dos.writeUTF(period.getStartDate().toString());
                dos.writeUTF(period.getEndDate().toString());
            });
        });
    }

    private void readWithException(DataInputStream dis, StorageConsumer<DataInputStream> action) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.accept(dis);
        }
    }

    private void deserializeContacts(DataInputStream dis, Resume resume) throws IOException {
        readWithException(dis, d -> {
            ContactType contactType = ContactType.valueOf(dis.readUTF());
            String value = dis.readUTF();
            resume.setContact(contactType, value);
        });
    }

    private void deserializeSections(DataInputStream dis, Resume resume) throws IOException {
        readWithException(dis, d -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            Section section = deserializeSection(sectionType, dis);
            resume.setSection(sectionType, section);
        });
    }

    private Section deserializeSection(SectionType sectionType, DataInputStream dis) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> deserializeTextSection(dis);
            case ACHIEVEMENT, QUALIFICATIONS -> deserializeListSection(dis);
            case EXPERIENCE, EDUCATION -> deserializeOrganizationSection(dis);
        };
    }

    private TextSection deserializeTextSection(DataInputStream dis) throws IOException {
        return new TextSection(dis.readUTF());
    }

    private ListSection deserializeListSection(DataInputStream dis) throws IOException {
        List<String> listSections = new ArrayList<>();
        readWithException(dis, d -> listSections.add(dis.readUTF()));
        return new ListSection(listSections);
    }

    private OrganizationSection deserializeOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        readWithException(dis, d -> {
            String name = dis.readUTF();
            String website = dis.readUTF();
            readWithException(d, dataInputStream -> {
                String periodName = d.readUTF();
                String description = d.readUTF();
                String startDate = d.readUTF();
                String endDate = d.readUTF();
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                Organization.Period period = new Organization.Period(periodName, description, start, end);
                organizations.add(new Organization(name, website, period));
            });
        });
        return new OrganizationSection(organizations);
    }
}
