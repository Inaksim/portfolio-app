package com.portfolioapp.portfolio.app.dto.form;


import lombok.Data;

@Data

public class UpdateProjectForm {
    private Long id;
    private String title;
    private String description;
    private String contentType;



}