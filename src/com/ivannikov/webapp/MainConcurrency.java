package com.ivannikov.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    public static final Object LOCK = new Object();
    public static final int THREAD_NUMBER = 10000;
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState()))
                .start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREAD_NUMBER);

        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
//            thread.join();
        }
        threads.forEach(thread1 -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//        Thread.sleep(500);
        System.out.println(counter);
    }

    private synchronized void inc() {
//            synchronized (this) {
//        synchronized (LOCK) {
        counter++;
//                wait();
//            }
    }
}
