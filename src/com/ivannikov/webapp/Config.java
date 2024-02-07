package com.ivannikov.webapp;

import com.ivannikov.webapp.storage.SqlStorage;
import com.ivannikov.webapp.storage.Storage;
import com.ivannikov.webapp.sql.SqlHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(new SqlHelper(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            ));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}