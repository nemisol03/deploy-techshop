package com.myshop.site;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtils {

    public static void saveFile(String dirName, String fileName, MultipartFile multipartFile) {
        Path path = Paths.get(dirName);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("Could not create directory: " + dirName);
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
