package com.ivannikov.webapp.storage;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR.toString(), new ObjectStreamSerializationStrategy<>()));
    }
}