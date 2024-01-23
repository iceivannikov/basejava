package com.ivannikov.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface StorageBiConsumer<T> {
    void accept(T t) throws IOException;
}
