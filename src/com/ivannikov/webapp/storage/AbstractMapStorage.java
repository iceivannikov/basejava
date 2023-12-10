package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.*;

public abstract class AbstractMapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put((resume.getUuid()), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        resumes.sort(getComparator());
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
