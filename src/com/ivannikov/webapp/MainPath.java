package com.ivannikov.webapp;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.*;

public class MainPath {
    public static void main(String[] args) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        byte[] b = "Ы".getBytes(charset);
        int[] result = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            result[i] = Byte.toUnsignedInt(b[i]);
            System.out.print(result[i] + " ");
        }

        try {
            FileSystem filesystem = FileSystems.getDefault();
            for (Path rootdir : filesystem.getRootDirectories()) {
                System.out.println(rootdir.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Path path1 = Paths.get("Storage/uuid1");
        Path path2 = Paths.get("Storage/uuid4");
        System.out.println(Files.exists(path1));
        System.out.println(Files.exists(path2));

        Path relative = Paths.get("MainPath.java");
        System.out.println("Файл: " + relative);
        //получение файловой системы
        System.out.println(relative.getFileSystem());

        InputStream inByte = new ByteArrayInputStream(new byte[]{48, 49, 50, 51});
        System.out.println(readAsString(inByte, StandardCharsets.US_ASCII));
    }

    public static String readAsString(InputStream inputStream, Charset charset) throws IOException {
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = buf.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString();
        }
    }
}
