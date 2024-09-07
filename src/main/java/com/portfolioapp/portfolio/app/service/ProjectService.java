package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;

import com.portfolioapp.portfolio.app.enitity.Project;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface ProjectService {

    ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws Exception;



    ResponseEntity<List<ProjectView>> getAllProjects(int page, int size, Principal principal);

    List<ProjectView> getMyProjects(Principal principal);


    ResponseEntity<String> deleteProject(Long projectId, Principal principal);

    ProjectView updateProject(UpdateProjectForm form, Principal principal);

    List<ProjectView>searchProjects(String keyword, Principal principal);




    ProjectView getProjectById(Long projectId, Principal principal);

    List<ProjectView> getTopProjects(int limit, Principal principal);

    ProjectView convertToProjectView(Project project);

    List<ProjectView> getProjectsByUserId(Long userId);
}
