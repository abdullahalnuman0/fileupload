package dev.springboot.filedemo.controller;

import dev.springboot.filedemo.service.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private FileStorageService fileStorageService;
    private InputStream resource;

    @GetMapping
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("Alhamdulillah api works !!");
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File cannot be empty!");
        }

        String fileName = null;
        try {
            fileName = fileStorageService.storeFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }


    @GetMapping(value = "/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveFile(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        resource = this.fileStorageService.getResource(imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("path") String file) {

        System.out.println("________________----------------______________-");
        System.out.println(file);

        return ResponseEntity.ok("File uploaded successfully: " + this.fileStorageService.removeImage(file));
    }



}
