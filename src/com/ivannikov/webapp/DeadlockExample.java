package com.ivannikov.webapp;

public class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        DeadlockExample deadlock = new DeadlockExample();
        new Thread(deadlock::operation1, "Thread_1").start();
        new Thread(deadlock::operation2, "Thread_2").start();
    }

    private void operation1() {
        synchronized (lock1) {
            System.out.println("lock1 acquired, waiting to acquire lock2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock2) {
                System.out.println("lock2 acquired");
                System.out.println("executing first operation");
            }
        }
    }

    private void operation2() {
        synchronized (lock2) {
            System.out.println("lock2 acquired, waiting to acquire lock1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock1) {
                System.out.println("lock1 acquired");
                System.out.println("executing first operation");
            }
        }
    }
}
