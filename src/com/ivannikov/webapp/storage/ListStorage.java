package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        if (storage.contains(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public void update(Resume resume) {
        if (storage.contains(resume)) {
            int index = getIndex(resume.getUuid());
            storage.set(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Optional<Resume> optionalResume = storage.stream().filter(r -> r.getUuid().equals(uuid)).findFirst();
        if (optionalResume.isPresent()) {
            return optionalResume.get();
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            storage.removeIf(resume -> resume.getUuid().equals(uuid));
        } catch (NullPointerException e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        return storage.toArray(resumes);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public List<Resume> getStorage() {
        return new ArrayList<>(storage);
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
