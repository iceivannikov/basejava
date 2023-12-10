package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int MAX_COUNT_RESUME = 10_000;

    protected final Resume[] storage = new Resume[MAX_COUNT_RESUME];
    protected int size;

    @Override
    public void doSave(Resume resume, Object index) {
        if (size >= MAX_COUNT_RESUME) {
            throw new StorageException("The storage is full, there is nowhere to save", resume.getUuid());
        } else {
            insertResume(resume, (Integer) index);
            size++;
        }
    }
    @Override
    public void doUpdate(Resume resume, Object index) {
            storage[(Integer) index] = resume;
    }
    @Override
    public Resume doGet(Object index) {
            return storage[(Integer) index];
    }
    @Override
    public void doDelete(Object index) {
            deleteResume((Integer) index);
            storage[size - 1] = null;
            size--;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = Arrays.asList(storage);
        resumeList.sort(getComparator());
        return resumeList;
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void deleteResume(int index);
}
