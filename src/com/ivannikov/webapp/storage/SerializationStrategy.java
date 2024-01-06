package com.ivannikov.webapp.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy<T> {

    void serialize(T o, OutputStream os) throws IOException;

    T deserialize(InputStream is) throws IOException;
}
