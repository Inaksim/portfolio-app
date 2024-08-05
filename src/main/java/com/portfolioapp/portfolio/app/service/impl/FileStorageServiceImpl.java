package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.service.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;


    @Value("${file.upload-dir}")
    private  String uploadPath;

    @Autowired
    public FileStorageServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

  /*  public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String fileUUID = UUID.randomUUID().toString();
            Path targetLocation = this.fileStorageLocation.resolve(fileUUID + "-" + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }*/

    public Resource loadFileAsResource(String fileName) throws Exception {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new Exception("File not found " + fileName);
        }
    }


    public String storeFile(MultipartFile uploadedFile) throws IOException {
        File uploadedDir = new File(uploadPath);
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + uploadedFile.getOriginalFilename();
        uploadedFile.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;

    }


}
