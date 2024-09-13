package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.portfolioapp.portfolio.app.service.ProjectService;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws Exception {
        return projectService.createProject(form, principal);
    }


    @GetMapping("/all")
    public ResponseEntity<List<ProjectView>> getAllProjects(@RequestParam int page, @RequestParam int size,
                                            Principal principal){
        return projectService.getAllProjects(page, size, principal);
    }

    @GetMapping("/top")
    public List<ProjectView> getTopProjects( @RequestParam(defaultValue = "10") int limit, Principal principal) {
        return projectService.getTopProjects(limit, principal);
    }

    @GetMapping("/get/my")
    public List<ProjectView> getMyProjects(Principal principal) {
        return projectService.getMyProjects(principal);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId, Principal principal) {
        return projectService.deleteProject(projectId, principal);
    }

    @PutMapping("/update")
    public ProjectView updateProject(UpdateProjectForm form, Principal principal) {
        return projectService.updateProject(form, principal);
    }

    @GetMapping ("/search")
    public List<ProjectView> searchProjects(@RequestParam String keyword, Principal principal) {
        return projectService.searchProjects(keyword, principal);
    }


    @GetMapping("/get/{projectId}")
    public ProjectView getProject(@PathVariable Long projectId, Principal principal) {
        return projectService.getProjectById(projectId, principal);

    }
    @GetMapping("/projects/{userId}")
    public List<ProjectView> getProjectsByUserId(@PathVariable Long userId) {
        return projectService.getProjectsByUserId(userId);
    }

}
