package com.example.helloworld.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {

    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize storage ", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        // install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"}).contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile multipartFile) {
        try {
            // save a file to a folder => use a service
            if (multipartFile.isEmpty()) {
                throw new RuntimeException("Fail to store empty file ");
            }
            // check file is image
            if (!isImageFile(multipartFile)) {
                throw new RuntimeException("You can only upload image");
            }
            // file must be <= 5mb
            float fileSizeInMegabytes = multipartFile.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5mb");
            }

            // Rename file
            String fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            String newFileName = generatedFileName + "." + fileExt;

            Path destinationFilePath = this.storageFolder.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();

            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return newFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Fail to store file ", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            // List all files in storageFolder
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder) && !path.toString().contains("._"))
                    .map(this.storageFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load stored files: ", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void deleteFile() {

    }

    @Override
    public void deleteAllFiles() {

    }
}
