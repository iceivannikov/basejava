package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.storage.serialization.Strategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Strategy strategy;

    protected FileStorage(String dir, Strategy strategy) {
        directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.strategy = strategy;
    }

    public File getDirectory() {
        return directory;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File not created ", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error ", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error ", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File not deleted ", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = getFiles(directory);
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        clearDirectory(directory);
    }

    @Override
    public int size() {
        File[] files = getFiles(directory);
        return files.length;
    }

    private void clearDirectory(File directory) {
        File[] files = getFiles(directory);
        for (File file : files) {
            if (file.isDirectory()) {
                clearDirectory(file);
            }
            doDelete(file);
        }
    }

    private File[] getFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory is empty");
        }
        return files;
    }
}
