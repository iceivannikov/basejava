package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_COUNT_RESUME = 10_000;
    protected final Resume[] storage = new Resume[MAX_COUNT_RESUME];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size >= MAX_COUNT_RESUME) {
            System.out.println("The storage is full, there is nowhere to save");
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("The resume exists. To update, use the update method");
        } else {
            insertResume(resume, index);
            System.out.printf("Resume with uuid: %s saved successfully%n", resume.getUuid());
        }
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.printf("Resume with uuid: %s, was successfully updated", resume.getUuid());
        } else {
            System.out.printf("No resume exists with this uuid: %s%n", resume.getUuid());
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("No resume exists with this uuid: %s%n", uuid);
        return null;
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
            System.out.printf("Resume with uuid: %s successfully deleted", uuid);
        } else {
            System.out.printf("No resume exists with this uuid: %s%n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without a null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void insertResume(Resume resume, int index);

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);
}
