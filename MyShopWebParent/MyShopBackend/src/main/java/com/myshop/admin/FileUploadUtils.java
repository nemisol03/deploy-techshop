package com.myshop.admin;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class FileUploadUtils {

    public static void saveFile(String dir, String fileName, MultipartFile multipartFile) {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Could not create directory: " + dir);
            }
        }
        try (InputStream fis = multipartFile.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(fis, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Could not save this file: " + fileName);
        }
    }

    public static void cleanDir(String dirName) {
        Path path = Paths.get(dirName);
        try {
            Files.list(path).forEach(file -> {
                if (!Files.isDirectory(file)) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Could not delete this file: " + file.getFileName());
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Could not list this directory: " + dirName);
        }

    }

    public static void deleteDir(String uploadDir) {
        Path path = Paths.get(uploadDir);
        if(Files.isDirectory(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.println("Couldn't delete this directory: " + uploadDir );
            }
        }
    }
}
