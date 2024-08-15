package com.portfolioapp.portfolio.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImgurService {

    String uploadImage(MultipartFile file) throws Exception;


}
