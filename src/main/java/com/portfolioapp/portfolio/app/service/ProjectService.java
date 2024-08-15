package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;

import com.portfolioapp.portfolio.app.enitity.Project;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProjectService {

    ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws IOException;

    List<ProjectView> getAllProjects();

    Page<Project> getAllProjects(int page, int size, String sortBy, boolean asc);

    List<ProjectView> getMyProjects(Principal principal);


    ResponseEntity<String> deleteProject(Long projectId, Principal principal);

    ProjectView updateProject(UpdateProjectForm form, Principal principal);

    List<ProjectView>searchProjects(String keyword);


    List<ProjectView> getProjectsByTag(String tagName);

    ProjectView getProjectById(Long projectId);

    List<ProjectView> getTopProjects(int limit);
}
