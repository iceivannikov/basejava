package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.storage.serialization.FileSystemSerializationStrategy;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class ObjectStreamFileStorageTest {
    private static final String TEMP_DIR = "/Users/viktor/IdeaProjects/basejava/TempDir";
    private static final Resume RESUME_1 = new Resume("uuid1", "name1");
    private static final Resume RESUME_2 = new Resume("uuid2", "name2");

    @Test
    public void objectStreamFileStorage() {
        File tempDirectory = new File(TEMP_DIR);
        //noinspection ResultOfMethodCallIgnored
        tempDirectory.mkdir();
        FileStorage tempDir = new FileStorage(TEMP_DIR, new FileSystemSerializationStrategy());

        tempDir.clear();

        tempDir.save(RESUME_1);
        tempDir.save(RESUME_2);

        Resume loadedResume1 = tempDir.get("uuid1");
        Resume loadedResume2 = tempDir.get("uuid2");

        Assertions.assertEquals(RESUME_1, loadedResume1);
        Assertions.assertEquals(RESUME_2, loadedResume2);

        cleanUpDirectory(tempDirectory);
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