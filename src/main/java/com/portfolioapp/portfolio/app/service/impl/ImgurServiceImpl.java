package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.service.ImgurService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.util.Base64;

@Service
public class ImgurServiceImpl implements ImgurService {

    @Value("${imgur.clientID}")
    private String clientId;

    @Override
    public String uploadImage(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();
        String encodedFile = Base64.getEncoder().encodeToString(fileBytes);

        String uri = "https://api.imgur.com/3/image";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Client-ID " + clientId);

        var requestEntity = new org.springframework.http.HttpEntity<>(new JSONObject().put("image", encodedFile).toString(), headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            org.json.JSONObject response = new org.json.JSONObject(responseEntity.getBody());
            return response.getJSONObject("data").getString("link");
        } else {
            throw new RuntimeException("Failed to upload image to Imgur");
        }
    }
}
