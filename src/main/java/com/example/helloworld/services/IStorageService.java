package com.example.helloworld.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public String storeFile(MultipartFile multipartFile);

    public Stream<Path> loadAll(); // load all file inside folder

    public byte[] readFileContent(String fileName);

    public void deleteFile();

    public void deleteAllFiles();
}
