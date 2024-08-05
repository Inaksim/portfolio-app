package com.portfolioapp.portfolio.app.dto.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateUserForm {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String bio;
    private String socialLinks;
    private MultipartFile avatar;
}
