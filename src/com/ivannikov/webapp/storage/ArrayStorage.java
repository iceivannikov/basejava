package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array-based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[2];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        Objects.requireNonNull(resume, "Resume object cannot be null");
        if (size < storage.length) {
            if (size == 0) {
                storage[size++] = resume;
            } else {
                String uuid = resume.getUuid();
                for (int i = 0; i < size; i++) {
                    if (!Objects.equals(storage[i].getUuid(), uuid)) {
                        storage[size++] = resume;
                        break;
                    } else {
                        System.out.println("The resume exists. To update, use the update method");
                    }
                }
            }
        } else {
            System.out.println("The storage is full, there is nowhere to save");
        }
    }

    public void update(Resume resume) {
        Objects.requireNonNull(resume, "Resume object cannot be null");
        String uuid = resume.getUuid();
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                storage[i] = resume;
            } else {
                System.out.printf("No resume exists with this uuid: %s%n", uuid);
            }
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return storage[i];
            }
        }
        System.out.printf("No resume exists with this uuid: %s%n", uuid);
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            } else {
                System.out.printf("No resume exists with this uuid: %s%n", uuid);
            }
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
}
