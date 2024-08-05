package com.portfolioapp.portfolio.app.dto.view;


import com.portfolioapp.portfolio.app.enitity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ProjectView {
    private Long id;
    private String title;
    private String description;;
    private String fileName;
    private String username;


    public ProjectView(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.description = project.getDescription();
        this.fileName = project.getFileName();
    }
}
