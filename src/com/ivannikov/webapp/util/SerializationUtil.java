package com.ivannikov.webapp.util;

import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.storage.serialization.Serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class SerializationUtil {
    public static <K, V> void serializeMap(DataOutputStream dos, Map<K, V> map, Serializer<V> valueSerializer) throws IOException {
        dos.writeInt(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            dos.writeUTF(entry.getKey().toString());
            valueSerializer.serialize(entry.getValue(), dos);
        }
    }

    public static void deserializeContacts(DataInputStream dis, Resume resume) throws IOException {
        int contactSize = dis.readInt();
        for (int i = 0; i < contactSize; i++) {
            ContactType contactType = ContactType.valueOf(dis.readUTF());
            String value = dis.readUTF();
            resume.addContact(contactType, value);
        }
    }

    public static void deserializeSections(DataInputStream dis, Resume resume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            Section section = deserializeSection(sectionType, dis);
            resume.addSection(sectionType, section);
        }
    }

    private static Section deserializeSection(SectionType sectionType, DataInputStream dis) throws IOException {
        return switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection().deserialize(dis);
            case ACHIEVEMENT, QUALIFICATIONS -> new ListSection().deserialize(dis);
            case EXPERIENCE, EDUCATION -> new OrganizationSection().deserialize(dis);
        };
    }
}
