package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array-based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (find(resume.getUuid()) == null) {
                storage[size++] = resume;
                System.out.printf("Resume with uuid: %s saved successfully%n", resume.getUuid());
            } else {
                System.out.println("The resume exists. To update, use the update method");
            }
        } else {
            System.out.println("The storage is full, there is nowhere to save");
        }
    }

    public void update(Resume updatedResume) {
        Resume resume = find(updatedResume.getUuid());
        if (resume != null) {
            resume = updatedResume;
            System.out.printf("Resume with uuid: %s, was successfully updated", resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        return find(uuid);
    }

    public void delete(String uuid) {
        Resume resume = find(uuid);
        if (resume != null) {
            resume = storage[size - 1];
            storage[size - 1] = null;
            size--;
            System.out.printf("Resume with uuid: %s successfully deleted", resume.getUuid());
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

    private Resume find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return storage[i];
            }
        }
        System.out.printf("No resume exists with this uuid: %s%n", uuid);
        return null;
    }
}
