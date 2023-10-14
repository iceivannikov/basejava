import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    int size;

    void clear() {
        for (int i = 0; i < size - 1; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        storage[size++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size - 1; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i;
        for (i = 0; i < size - 1; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                break;
            }
        }
        for (int j = i; j < size - 1; j++) {
            storage[j] = storage[j + 1];
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
