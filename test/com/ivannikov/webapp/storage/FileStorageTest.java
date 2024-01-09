package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.FileSystemSerializationStrategy;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR.toString(), new FileSystemSerializationStrategy()));
    }
}