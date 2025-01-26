package dev.springboot.filedemo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.UUID;

@Service
public class FileStorageService {


    @Value("${project.image}")
    private String path;


    // Save the file
    public String storeFile(MultipartFile file) throws IOException {

        //File name
        String name = file.getOriginalFilename();

        //random name generate
        String randomID = UUID.randomUUID().toString();
        randomID += name.substring(name.lastIndexOf('.'));


        //Full path
        String fullPath = path + File.separator + randomID;


        //create folder if not create
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);

        return randomID;
    }


    public InputStream getResource(String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }


    /// TODO: delete old image
    public boolean removeImage(String imageName) {

        try {
            return Files.deleteIfExists(Path.of(path + File.separator + imageName));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}