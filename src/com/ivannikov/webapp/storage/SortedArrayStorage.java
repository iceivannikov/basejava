package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume resume, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = resume;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "name");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
