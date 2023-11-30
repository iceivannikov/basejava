package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class MapStorage extends AbstractStorage {

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
    protected Object getSearchKey(String uuid) {
        Set<Map.Entry<String, Resume>> entries = storage.entrySet();
        for (Map.Entry<String, Resume> entry : entries) {
            if (entry.getKey().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        return storage.values().toArray(resumes);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
