package com.ivannikov.webapp.storage;

public class FullNameMapStorage extends AbstractMapStorage {
    @Override
    protected Object getSearchKey(String fullName) {
        return fullName;
    }
}
