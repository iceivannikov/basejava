package com.ivannikov.webapp;

public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();

        new Thread(deadlock::operation1).start();
        new Thread(deadlock::operation2).start();
    }

    public void operation1() {
        operation(lock1, lock2, "executing first operation");
    }

    public void operation2() {
        operation(lock2, lock1, "executing second operation");
    }

    private void operation(Object firstLock, Object secondLock, String message) {
        synchronized (firstLock) {
            System.out.println(firstLock + " acquired, waiting to acquire " + secondLock);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (secondLock) {
                System.out.println(secondLock + " acquired");
                System.out.println(message);
            }
        }
    }
}
