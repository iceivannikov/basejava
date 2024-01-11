package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.*;
import com.ivannikov.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializationStrategy implements Strategy {

    private final XmlParser xmlParser;

    public XmlSerializationStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, OrganizationSection.class,
                TextSection.class, ListSection.class, Organization.Period.class
        );
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
