package com.ivannikov.webapp.storage;

public class UuidMapStorage extends AbstractMapStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
}
