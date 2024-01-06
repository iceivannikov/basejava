package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;

import java.io.*;

public class ObjectStreamSerializationStrategy<T> implements SerializationStrategy<T> {

    @Override
    public void serialize(T o, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(o);

        }
    }

    @Override
    public T deserialize(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)){
            //noinspection unchecked
            return (T) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
