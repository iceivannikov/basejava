package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.util.SerializationUtil;

import java.io.*;
import java.util.Map;

public class DataOutputSerializationStrategy implements Strategy {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            SerializationUtil.serializeMap(dos, contacts, new ContactSerializer());
            Map<SectionType, Section> sections = resume.getSections();
            SerializationUtil.serializeMap(dos, sections, new SectionSerializer());
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            SerializationUtil.deserializeContacts(dis, resume);
            SerializationUtil.deserializeSections(dis, resume);
            return resume;
        }
    }
}
