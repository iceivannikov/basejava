package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";

    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static Resume RESUME_3 = new Resume(UUID_3);
    private final static Resume RESUME_4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        int expected = 0;
        int actual = storage.size();
        assertEquals(expected, actual);
    }

    @Test(expected = StorageException.class)
    public void overflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.MAX_COUNT_RESUME; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail();
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4);
        storage.save(resume);
        int expected = 4;
        int actual = storage.size();
        assertEquals(expected, actual);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    public void update() {
        Resume expected = new Resume(UUID_1);
        storage.update(expected);
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        int expected = 2;
        int actual = storage.size();
        assertEquals(expected, actual);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        int expected = 3;
        int actual = resumes.length;
        assertEquals(expected, actual);
        assertEquals(RESUME_1, resumes[0]);
        assertEquals(RESUME_2, resumes[1]);
        assertEquals(RESUME_3, resumes[2]);
    }

    @Test
    public void size() {
        int expected = 3;
        int actual = storage.size();
        assertEquals(expected, actual);
    }
}