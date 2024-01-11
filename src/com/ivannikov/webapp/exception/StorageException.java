package com.ivannikov.webapp.exception;

public class StorageException extends RuntimeException {

    private String uuid;

    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }
    public StorageException(String message, Exception e) {
        this(message, null, e);
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
