package com.ivannikov.webapp.storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface StorageBiConsumer<T, U> {
    void accept(T t, U u) throws IOException;
}
