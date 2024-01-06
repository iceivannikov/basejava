package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.ResumeTestData;
import com.ivannikov.webapp.exception.ExistStorageException;
import com.ivannikov.webapp.exception.NotExistStorageException;
import com.ivannikov.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    public static final String TEMP_DIR = "/Users/viktor/IdeaProjects/basejava/tempDir";
    protected static final File STORAGE_DIR = new File("/Users/viktor/IdeaProjects/basejava/Storage");
    protected final Storage storage;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";

    private final static String NAME_1 = "name1";
    private final static String NAME_2 = "name2";
    private final static String NAME_3 = "name3";
    private final static String NAME_4 = "name4";

    private final static Resume RESUME_1 = ResumeTestData.newResume(UUID_1, NAME_1);
    private final static Resume RESUME_2 = ResumeTestData.newResume(UUID_2, NAME_2);
    private final static Resume RESUME_3 = ResumeTestData.newResume(UUID_3, NAME_3);
    private final static Resume RESUME_4 = ResumeTestData.newResume(UUID_4, NAME_4);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void init() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4, NAME_4);
        storage.save(resume);
        assertSize(4);
        assertGet(resume);
    }

    @Test
    public void update() {
        Resume expected = new Resume(UUID_1, "new name");
        storage.update(expected);
        Resume actual = storage.get(UUID_1);
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = storage.getAllSorted();
        assertEquals(3, storage.size());
        assertEquals(expected, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void testObjectStreamPathStorage() throws IOException {
        Path path = Paths.get(TEMP_DIR);
        Files.createDirectory(path);
        SerializationStrategy<Resume> serializationStrategy = new ObjectStreamSerializationStrategy<>();
        AbstractPathStorage tempDir = new ObjectStreamPathStorage(TEMP_DIR, serializationStrategy);

        tempDir.save(RESUME_1);
        tempDir.save(RESUME_2);

        Resume loadedResume1 = tempDir.get(UUID_1);
        Resume loadedResume2 = tempDir.get(UUID_2);

        Assertions.assertEquals(RESUME_1, loadedResume1);
        Assertions.assertEquals(RESUME_2, loadedResume2);

        cleanUpDirectory(path);
    }

    @Test
    public void testObjectStreamFileStorage() {
        File tempDirectory = new File(TEMP_DIR);
        //noinspection ResultOfMethodCallIgnored
        tempDirectory.mkdir();
        SerializationStrategy<Resume> serializationStrategy = new ObjectStreamSerializationStrategy<>();
        AbstractFileStorage tempDir = new ObjectStreamFileStorage(tempDirectory.toString(), serializationStrategy);

        tempDir.save(RESUME_1);
        tempDir.save(RESUME_2);

        Resume loadedResume1 = tempDir.get(UUID_1);
        Resume loadedResume2 = tempDir.get(UUID_2);

        Assertions.assertEquals(RESUME_1, loadedResume1);
        Assertions.assertEquals(RESUME_2, loadedResume2);

        cleanUpDirectory(tempDirectory);
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void cleanUpDirectory(Path directory) {
        try (Stream<Path> stream = Files.list(directory)) {
            List<Path> files = stream.toList();
            files.forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cleanUpDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
        //noinspection ResultOfMethodCallIgnored
        directory.delete();
    }
}