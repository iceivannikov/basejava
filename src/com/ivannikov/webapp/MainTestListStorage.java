package com.ivannikov.webapp;

import com.ivannikov.webapp.model.Resume;
import com.ivannikov.webapp.storage.ListStorage;
import com.ivannikov.webapp.storage.Storage;

public class MainTestListStorage {

    private static final Storage STORAGE = new ListStorage();
    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r5 = new Resume("uuid5");

        STORAGE.save(r1);
        STORAGE.save(r2);
        STORAGE.save(r3);
        STORAGE.save(r5);


        System.out.println("Get r1: " + STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + STORAGE.size());

//        System.out.println("Get dummy: " + STORAGE.get("dummy"));

        STORAGE.update(r1);

        printAll();
        STORAGE.delete(r1.getUuid());
        printAll();
        STORAGE.delete(r3.getUuid());
        printAll();
        STORAGE.clear();
        printAll();

        System.out.println("Size: " + STORAGE.size());
    }
    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
