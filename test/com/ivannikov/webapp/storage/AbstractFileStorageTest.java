package com.ivannikov.webapp.storage;

public class AbstractFileStorageTest extends AbstractStorageTest {

    public AbstractFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR.toString()));
    }
}