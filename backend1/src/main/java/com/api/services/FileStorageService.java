package com.api.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileStorageService {
    String storeFile(String dirName,MultipartFile file);

    boolean fileExists(String path);

    File getFile(String path);
    File createDir(String path) throws IOException;
    List<String> getFilesNames(String dirPath) throws IOException;

    void renameFile(String path,String newPath) throws IOException;

    void deleteFile(String path) throws IOException;
    void deleteDirectory(String path) throws IOException;

    /**
     * @param path buildingName+"/"+floorName
     * @return image as Resource
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    Resource loadFileAsResource(String path) throws FileNotFoundException, MalformedURLException;
}
