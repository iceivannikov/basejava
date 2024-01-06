package com.ivannikov.webapp.storage;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.toString(), new ObjectStreamSerializationStrategy<>()));
    }
}