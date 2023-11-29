package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.MAX_COUNT_RESUME; i++) {
                super.storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume());
    }
}