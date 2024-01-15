package com.ivannikov.webapp.storage.serialization;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Serializer<T> {
    void serialize(T object, DataOutputStream dos) throws IOException;
}
