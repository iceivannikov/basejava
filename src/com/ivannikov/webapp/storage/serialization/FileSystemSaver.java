package com.ivannikov.webapp.storage.serialization;

import com.ivannikov.webapp.model.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemSaver {
    private final Strategy strategy;

    public FileSystemSaver(Strategy strategy) {
        this.strategy = strategy;
    }

    public void saveToFile(Resume resume, Path path) throws IOException {
        byte[] data = strategy.serialize(resume);
        Files.write(path, data);
    }
    public void saveToFile(Resume resume, File file) throws IOException {
        byte[] data = strategy.serialize(resume);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

    public Resume loadFromFile(Path path) throws IOException {
        byte[] data = Files.readAllBytes(path);
        return (Resume) strategy.deserialize(data);
    }
    public Resume loadFromFile(File file) throws IOException {
        byte[] data;
        try (FileInputStream fis = new FileInputStream(file)) {
            data = fis.readAllBytes();
        }
        return (Resume) strategy.deserialize(data);
    }
}
