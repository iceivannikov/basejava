package com.ivannikov.webapp;

import java.io.*;

public class MainFile {
    public static void main(String[] args) throws IOException {
//        File file = new File("/Users/viktor/IdeaProjects/basejava/.gitignore");
//        System.out.println("Canonical path: " + file.getCanonicalFile());
//        System.out.println("Absolute path: " + file.getAbsoluteFile());
//        System.out.println("File name is: " + file.getName());
//        System.out.println("Is this file? - " + file.isFile());
//        System.out.println("Is this directory? - " + file.isDirectory());
//        System.out.println("Read? " + file.canRead());
//        System.out.println("Write? " + file.canWrite());
//        System.out.println("Execute? " + file.canExecute());
//        System.out.println("Exists? " + file.exists());
//        System.out.println("Parent is: " + file.getParent());

        File dir = new File("./src/com/ivannikov/webapp");
        System.out.println("-".repeat(10) + "Print files directory: " + dir + "-".repeat(10));
        printFiles(dir, 0);

//        File fileName = new File("/Users/viktor/IdeaProjects/basejava/Storage/text.txt");
//
//        //Строка, которая будет записана в файл
//        String data = "Some data to be written and read.\n";
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
//            System.out.println("Write some data to file: " + fileName.getName());
//
//            // Несколько раз записать строку
//            for (int i = (int) (Math.random() * 10); --i >= 0; )
//                bw.write(data);
//            bw.close();
//
//            // Считываем результат
//            BufferedReader br = new BufferedReader(new FileReader(fileName));
//            String s;
//            int count = 0;
//            System.out.println("Read data from file: " + fileName.getName());
//
//            // Считывать данные, отображая на экран
//            while ((s = br.readLine()) != null)
//                System.out.println("row " + ++count + " read: " + s);
//            br.close();
//        } catch (Exception e) {
//            throw new IOException(e);
//        }
    }

//    private static void printFiles(File directory) {
//        if (directory.isDirectory()) {
//            System.out.println("Directory: " + directory);
//            File[] files = directory.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isDirectory()) {
//                        printFiles(file);
//                    } else {
//                        System.out.println("          File: " + file);
//                    }
//                }
//            }
//        }
//    }

    private static void printFiles(File directory, int depth) {
        if (directory.isDirectory()) {
            System.out.println(getIndent(depth) + "Directory: " + directory.getName());
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        printFiles(file, depth + 1);
                    } else {
                        System.out.println(getIndent(depth + 1) + "File: " + file.getName());
                    }
                }
            }
        }
    }

    private static String getIndent(int depth) {
        return "    ".repeat(Math.max(0, depth));
    }
}