package dev.springboot.filedemo.controller;

import dev.springboot.filedemo.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping
    public ResponseEntity<?> home(){
        return ResponseEntity.ok("Alhamdulillah api works !!");
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File cannot be empty!");
        }

        String fileName = fileStorageService.storeFile(file, true);
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("path") String file) {

        System.out.println("________________----------------______________-");
        System.out.println(file);

        return ResponseEntity.ok("File uploaded successfully: " + this.fileStorageService.removeImage(file));
    }



}
