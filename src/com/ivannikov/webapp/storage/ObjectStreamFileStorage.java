package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.io.*;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    private final SerializationStrategy<Resume> serializationStrategy;
    protected ObjectStreamFileStorage(String directory, SerializationStrategy<Resume> serializationStrategy) {
        super(directory);
        this.serializationStrategy = serializationStrategy;
    }

    public SerializationStrategy<Resume> getSerializationStrategy() {
        return serializationStrategy;
    }

    @Override
    protected void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            serializationStrategy.serialize(resume, objectOutputStream);
        }
    }

    @Override
    protected Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return serializationStrategy.deserialize(objectInputStream);
        }
    }
}
