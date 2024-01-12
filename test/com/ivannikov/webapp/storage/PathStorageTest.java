package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.FileSystemSerializationStrategy;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new FileSystemSerializationStrategy()));
    }
}