package com.portfolioapp.portfolio.app.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    Resource loadFileAsResource(String fileName) throws Exception;

    String storeFile(MultipartFile uploadedFile) throws IOException;

}
