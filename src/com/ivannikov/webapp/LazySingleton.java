package com.ivannikov.webapp;

public class LazySingleton {

    private LazySingleton() {}

    private static final class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
