package com.portfolioapp.portfolio.app.dto.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpForm {
    private String firstName;
    private String username;
    private String email;
    private String password;
    private MultipartFile avatar;
    private String bio;
    private String socialLinks;

}
