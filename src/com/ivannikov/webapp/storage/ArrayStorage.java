package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array-based storage for Resumes
 */
public class ArrayStorage {
    public static final int MAX_COUNT_RESUME = 10_000;
    private final Resume[] storage = new Resume[MAX_COUNT_RESUME];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size < MAX_COUNT_RESUME) {
            if (getIndex(resume.getUuid()) == -1) {
                storage[size++] = resume;
                System.out.printf("Resume with uuid: %s saved successfully%n", resume.getUuid());
            } else {
                System.out.println("The resume exists. To update, use the update method");
            }
        } else {
            System.out.println("The storage is full, there is nowhere to save");
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.printf("Resume with uuid: %s, was successfully updated", resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.printf("Resume with uuid: %s successfully deleted", uuid);
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

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        System.out.printf("No resume exists with this uuid: %s%n", uuid);
        return -1;
    }
}
