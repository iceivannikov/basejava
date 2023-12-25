package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.exception.StorageException;
import com.ivannikov.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final static Logger LOG = Logger.getLogger(AbstractFileStorage.class.getName());
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    public File getDirectory() {
        return directory;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (file.createNewFile()) {
                LOG.info("File created");
            }
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (file.delete()) {
            LOG.info("File deleted");
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
        List<Resume> list = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            try {
                list.add(doRead(file));
            } catch (IOException e) {
                throw new StorageException("IO error", file.getName(), e);
            }
        }
        return list;
    }

    @Override
    public void clear() {
        clearDirectory(directory);
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;
    protected abstract Resume doRead(File file) throws IOException;

    private void clearDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    clearDirectory(file);
                }
                if (file.delete()) {
                    LOG.info("File deleted");
                }
            }
        }
    }
}
