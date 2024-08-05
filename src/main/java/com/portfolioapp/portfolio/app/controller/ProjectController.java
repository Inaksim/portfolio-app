package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.enitity.Project;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.portfolioapp.portfolio.app.service.ProjectService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws IOException {
        return projectService.createProject(form, principal);
    }


    @GetMapping("/all")
    public Page<Project> getAllProjects( @RequestParam int page,
                                             @RequestParam int size,
                                             @RequestParam String sortBy,
                                             @RequestParam boolean asc){
        return projectService.getAllProjects(page, size, sortBy, asc);
    }

    @GetMapping("/my")
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
    public List<ProjectView> searchProjects(@RequestParam String keyword) {
        return projectService.searchProjects(keyword);
    }

    @GetMapping("/tag/{tagName}")
    public List<ProjectView> getProjectsByTag(@PathVariable String tagName) {
        return projectService.getProjectsByTag(tagName);
    }

    @GetMapping("/{projectId}")
    public ProjectView getProject(@PathVariable Long projectId) {
        return projectService.getProjectById(projectId);

    }
}
