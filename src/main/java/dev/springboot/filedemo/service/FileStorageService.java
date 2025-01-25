package dev.springboot.filedemo.service;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class FileStorageService {

    private static String UPLOAD_DIR = null;


    // Save the file
    public String storeFile(MultipartFile file, boolean isUser) {

        try {
            findUploadDir();

            String imageNameWidthPath = UPLOAD_DIR + File.separator +
                    (isUser ? "user_" : "contact_") +
                    System.currentTimeMillis() + new Random().nextInt(1000) + "." + file.getContentType().split("/")[1];

            Files.copy(file.getInputStream(), Path.of(imageNameWidthPath),
                    StandardCopyOption.REPLACE_EXISTING);

            return imageNameWidthPath;
        } catch (Exception e) {
            return null;
        }
    }

    private void findUploadDir() {
        try {
            if (UPLOAD_DIR == null)
                UPLOAD_DIR = new ClassPathResource("static").getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    /// TODO: delete old image
    public boolean removeImage(String imagePath) {

        try {
            return Files.deleteIfExists(Path.of(imagePath));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}