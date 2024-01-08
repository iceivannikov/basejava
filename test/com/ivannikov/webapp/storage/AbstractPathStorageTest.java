package com.ivannikov.webapp.storage;

public class AbstractPathStorageTest extends AbstractStorageTest {

    public AbstractPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.toString()));
    }
}