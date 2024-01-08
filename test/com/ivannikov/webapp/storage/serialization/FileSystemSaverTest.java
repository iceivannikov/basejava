package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.Resume;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class FileSystemSaverTest {

    private static final String DIR = "/Users/viktor/IdeaProjects/basejava/DirSave";
    private static final Resume RESUME = new Resume("uuid", "name");
    @Test
    public void javaFileSerialization() throws IOException {
        FileSystemSaver saver = new FileSystemSaver(new JavaSerializationStrategy());
        File file = new File(DIR);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();

        saver.saveToFile(RESUME, file);

        Resume loadResume1 = saver.loadFromFile(file);

        assertEquals(RESUME, loadResume1);

        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void javaPathSerialization() throws IOException {
        FileSystemSaver saver = new FileSystemSaver(new JavaSerializationStrategy());
        Path path = Paths.get(DIR);
        Files.createFile(path);

        saver.saveToFile(RESUME, path);

        Resume loadResume1 = saver.loadFromFile(path);

        assertEquals(RESUME, loadResume1);

        Files.deleteIfExists(path);
    }

}