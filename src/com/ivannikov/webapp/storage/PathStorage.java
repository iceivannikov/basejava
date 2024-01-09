package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.storage.serialization.Strategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final Strategy strategy;

    protected PathStorage(String dir, Strategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    public Path getDirectory() {
        return directory;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.toAbsolutePath(), path.getFileName().toString(), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("Path write error ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            throw new StorageException("Path read error ", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString()).resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected List<Resume> doGetAll() {
        try (Stream<Path> stream = getStream()) {
            return stream.map(this::doGet).collect(Collectors.toList());
        }
    }

    @Override
    public void clear() {
        List<Path> files;
        try (Stream<Path> stream = getStream()) {
            files = stream.toList();
        }
        files.forEach(this::doDelete);
    }

    @Override
    public int size() {
        try (Stream<Path> stream = getStream()) {
            return stream.toArray().length;
        }
    }

    private Stream<Path> getStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory error", null, e);
        }
    }
}

