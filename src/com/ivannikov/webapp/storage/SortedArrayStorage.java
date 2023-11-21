package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, 0, storage, 0, insertIdx);
        storage[insertIdx] = resume;
        size++;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx - 1);
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
    }
}
