package com.ivannikov.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/viktor/IdeaProjects/basejava/.gitignore");
        System.out.println("Canonical path: " + file.getCanonicalFile());
        System.out.println("Absolute path: " + file.getAbsoluteFile());
        System.out.println("File name is: " + file.getName());
        System.out.println("Is this file? - " + file.isFile());
        System.out.println("Is this directory? - " + file.isDirectory());
        System.out.println("Read? " + file.canRead());
        System.out.println("Write? " + file.canWrite());
        System.out.println("Execute? " + file.canExecute());
        System.out.println("Exists? " + file.exists());
        System.out.println("Parent is: " + file.getParent());

        File dir = new File("./src/com/ivannikov/webapp");
        System.out.println("-".repeat(10) + "Print files directory: " + dir + "-".repeat(10));
        printFiles(dir);
    }

    private static void printFiles(File directory) {
        if (directory.isDirectory()) {
            System.out.println("Directory: " + directory);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        printFiles(file);
                    } else {
                        System.out.println("File: " + file);
                    }
                }
            }
        }
    }
}