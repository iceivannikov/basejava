package com.ivannikov.webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR));
    }
}