package com.ivannikov.webapp.storage.serialization;

import java.io.IOException;

public interface Strategy {

    byte[] serialize(Object object) throws IOException;
    Object deserialize(byte[] data) throws IOException;
}
