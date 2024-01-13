package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.DataOutputSerializationStrategy;

public class DataStorageTest extends AbstractStorageTest {

    public DataStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataOutputSerializationStrategy()));
    }
}