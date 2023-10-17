import java.util.Arrays;
import java.util.Objects;

/**
 * Array-based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    int size;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size++] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                storage[i] = storage[size - 1];
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without a null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
