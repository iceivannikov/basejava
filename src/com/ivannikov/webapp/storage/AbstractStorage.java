package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    private final static Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid)
            .thenComparing(Resume::getUuid);

    public final void save(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public final void update(Resume resume) {
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public final Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> list = doGetAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> doGetAll();

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
