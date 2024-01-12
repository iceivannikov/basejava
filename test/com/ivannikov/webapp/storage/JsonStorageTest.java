package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.JsonSerializationStrategy;

public class JsonStorageTest extends AbstractStorageTest {

    public JsonStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonSerializationStrategy()));
    }
}