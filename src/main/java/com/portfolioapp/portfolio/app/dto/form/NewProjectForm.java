package com.portfolioapp.portfolio.app.dto.form;

import com.portfolioapp.portfolio.app.enitity.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class NewProjectForm {
    private String title;
    private String description;
    private String contentType;
    private MultipartFile file;
    private List<Tag> tags;
}
