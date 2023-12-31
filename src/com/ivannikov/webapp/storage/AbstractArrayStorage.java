package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int MAX_COUNT_RESUME = 10_000;

    protected final Resume[] storage = new Resume[MAX_COUNT_RESUME];
    protected int size;

    @Override
    public void doSave(Resume resume, Integer index) {
        if (size >= MAX_COUNT_RESUME) {
            throw new StorageException("The storage is full, there is nowhere to save", resume.getUuid());
        } else {
            insertResume(resume, index);
            size++;
        }
    }
    @Override
    public void doUpdate(Resume resume, Integer index) {
            storage[index] = resume;
    }
    @Override
    public Resume doGet(Integer index) {
            return storage[index];
    }
    @Override
    public void doDelete(Integer index) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void deleteResume(int index);
}
