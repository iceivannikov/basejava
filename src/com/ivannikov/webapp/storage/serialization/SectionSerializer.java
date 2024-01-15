package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.Section;

import java.io.DataOutputStream;
import java.io.IOException;

public class SectionSerializer implements Serializer<Section> {
    @Override
    public void serialize(Section section, DataOutputStream dos) throws IOException {
        section.serialize(dos);
    }
}
