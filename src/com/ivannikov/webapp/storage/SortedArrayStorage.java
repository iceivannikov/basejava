package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume) {
        int index = Math.abs(getIndex(resume.getUuid())) - 1;
        System.arraycopy(storage, 0, storage, 0, index);
        storage[index] = resume;
        size++;
        System.arraycopy(storage, index, storage, index + 1, size - index - 1);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
        size--;
    }
}
