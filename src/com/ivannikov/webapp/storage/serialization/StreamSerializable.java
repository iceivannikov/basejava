package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.Section;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface StreamSerializable {
    void serialize(DataOutputStream dos) throws IOException;
    Section deserialize(DataInputStream dis) throws IOException;
}
