package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
