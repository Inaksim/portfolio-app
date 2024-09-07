package com.portfolioapp.portfolio.app.dto.form;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Setter
@Builder
public class NewProjectForm {
    private String title;
    private String content;
    private MultipartFile cover;

}
