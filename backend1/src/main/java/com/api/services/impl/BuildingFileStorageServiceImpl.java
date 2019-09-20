package com.api.services.impl;

import com.api.config.properties.FileStorageProperties;
import com.api.config.properties.LogMessages;
import com.api.exceptions.FileStorageException;
import com.api.services.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuildingFileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public BuildingFileStorageServiceImpl(FileStorageProperties properties) {

        this.fileStorageLocation = Paths.get(properties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(String path, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(path);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public boolean fileExists(String relativePath) {
        return Files.exists(this.fileStorageLocation.resolve(relativePath));
    }

    @Override
    public File getFile(String path) {
        return fileStorageLocation.resolve(path).toFile();
    }

    @Override
    public File createDir(String path) throws IOException {
        return Files.createDirectory(fileStorageLocation.resolve(path)).toFile();
    }

    @Override
    public List<String> getFilesNames(String dirPath) throws IOException {
        return Files.walk(
                (!dirPath.equals("")) ?
                        fileStorageLocation.resolve(dirPath) : fileStorageLocation
                , 1)
                .filter(path -> !path.endsWith(dirPath) && !fileStorageLocation.equals(path))
                .map(Path::toFile)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void renameFile(String path, String newPath) throws IOException {
        Files.move(fileStorageLocation.resolve(path), fileStorageLocation.resolve(newPath));
    }

    @Override
    public void deleteFile(String path) throws IOException {
        Files.delete(fileStorageLocation.resolve(path));
    }

    @Override
    public void deleteDirectory(String path) throws IOException {
        Files.walk(fileStorageLocation.resolve(path))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Override
    public Resource loadFileAsResource(String fileName) throws FileNotFoundException, MalformedURLException {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists())
            return resource;
        else {
            throw new FileNotFoundException(filePath.toString());
        }
    }
}
