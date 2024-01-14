package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.*;

import java.io.*;
import java.util.Map;

public class DataOutputSerializationStrategy implements Strategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                entry.getValue().serialize(dos);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                resume.addContact(contactType, value);
            }
            deserializeMap(resume, dis);
            return resume;
        }
    }

    private void deserializeMap(Resume resume, DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            try {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = deserializeSection(sectionType, dis);
                resume.addSection(sectionType, section);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Section deserializeSection(SectionType sectionType, DataInputStream dis) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection().deserialize(dis);
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection().deserialize(dis);
            case EXPERIENCE, EDUCATION -> new OrganizationSection().deserialize(dis);
        };
    }
}
