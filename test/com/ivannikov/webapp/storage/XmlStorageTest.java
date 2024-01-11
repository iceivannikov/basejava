package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.storage.serialization.XmlSerializationStrategy;

public class XmlStorageTest extends AbstractStorageTest {

    public XmlStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new XmlSerializationStrategy()));
    }
}