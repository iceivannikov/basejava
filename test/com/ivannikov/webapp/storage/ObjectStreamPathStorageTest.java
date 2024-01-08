package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.model.Resume;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ObjectStreamPathStorageTest {
    private static final String TEMP_DIR = "/Users/viktor/IdeaProjects/basejava/TempDir";
    private static final Resume RESUME_1 = new Resume("uuid1", "name1");
    private static final Resume RESUME_2 = new Resume("uuid2", "name2");

    @Test
    public void objectStreamPathStorage() throws IOException {
        Path path = Paths.get(TEMP_DIR);
        Files.createDirectory(path);
        AbstractPathStorage tempDir = new ObjectStreamPathStorage(TEMP_DIR);

        tempDir.save(RESUME_1);
        tempDir.save(RESUME_2);

        Resume loadedResume1 = tempDir.get("uuid1");
        Resume loadedResume2 = tempDir.get("uuid2");

        Assertions.assertEquals(RESUME_1, loadedResume1);
        Assertions.assertEquals(RESUME_2, loadedResume2);

        cleanUpDirectory(path);
    }

    private void cleanUpDirectory(Path directory) {
        try (Stream<Path> stream = Files.list(directory)) {
            List<Path> files = stream.toList();
            files.forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}