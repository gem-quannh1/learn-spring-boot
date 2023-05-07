package com.example.helloworld.controllers;

import com.example.helloworld.models.ResponseObject;
import com.example.helloworld.services.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1")
public class FileUploadController {


    public FileUploadController() {
    }

    // Injected Storage Service Here
    @Autowired
    private ImageStorageService imageStorageService;


    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        try {
            String generatedFileName = imageStorageService.storeFile(multipartFile);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file success", generatedFileName)
            );

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("ok", exception.getMessage(), "")
            );
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = imageStorageService.readFileContent(fileName);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
