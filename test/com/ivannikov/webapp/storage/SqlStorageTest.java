package com.ivannikov.webapp.storage;

import com.ivannikov.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage(
                Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword())
        );
    }
}