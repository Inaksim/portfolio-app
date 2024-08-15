package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.enitity.Project;
import com.portfolioapp.portfolio.app.enitity.Tag;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.ProjectRepository;
import com.portfolioapp.portfolio.app.repository.TagRepository;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.service.FileStorageService;
import com.portfolioapp.portfolio.app.service.ProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

   private FileStorageService fileStorageService;
   private UserRepository userRepository;
   private ProjectRepository projectRepository;
   private ModelMapper modelMapper;
   private TagRepository tagRepository;
    public ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws IOException {
        String filePath = fileStorageService.storeFile(form.getFile());
        Project project = new Project();
        project.setTitle(form.getTitle());
        project.setDescription(form.getDescription());
        project.setFileName(filePath);
        project.setUser(userRepository.findByEmail(principal.getName()).orElseThrow());
        project.setCreatedAt(LocalDateTime.now());
        projectRepository.saveAndFlush(project);
        return ResponseEntity.ok("Created");
    }


    public List<ProjectView> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream()
                .map(project -> new ProjectView(
                        project.getId(),
                        project.getTitle(),
                        project.getDescription(),
                        project.getFileName(),
                        project.getUser().getUsername()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Page<Project> getAllProjects(int page, int size, String sortBy, boolean asc) {
        PageRequest pageRequest = PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return projectRepository.findAll(pageRequest);

    }

    @Override
    public List<ProjectView> getMyProjects(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Project> projects = projectRepository.findProjectsByUser(user);

        return projects.stream()
                .map(project -> new ProjectView(
                        project.getId(),
                        project.getTitle(),
                        project.getDescription(),
                        project.getFileName(),
                        project.getUser().getUsername()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> deleteProject(Long projectId, Principal principal) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        if(project.getUser() == user) {
            projectRepository.delete(project);
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("not Success");

    }

    @Override
    public ProjectView updateProject(UpdateProjectForm form, Principal principal) {
        Project project = projectRepository.findById(form.getId()).orElseThrow();
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        if(project.getUser() == user) {
            project.setTitle(form.getTitle());
            project.setDescription(form.getDescription());
            project.setUpdatedAt(LocalDateTime.now());
            Project result = projectRepository.saveAndFlush(project);
            return modelMapper.map(result, ProjectView.class);
        }

        return modelMapper.map(project, ProjectView.class);

    }

    @Override
    public List<ProjectView> searchProjects(String keyword) {
        List<Project> projects = projectRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        return Collections.singletonList(modelMapper.map(projects, ProjectView.class));
    }

    @Override
    public List<ProjectView> getProjectsByTag(String tagName) {
        Tag tag =  tagRepository.findByName(tagName).orElseThrow();
        List<Project> projects = tag.getProjects();
        return Collections.singletonList(modelMapper.map(projects, ProjectView.class));
    }

    @Override
    public ProjectView getProjectById(Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        return modelMapper.map(project, ProjectView.class);
    }

    @Override
    public List<ProjectView> getTopProjects(int limit) {
        List<Project> projects = projectRepository.findTopProjectsByLikes(limit);
        return projects.stream()
                .map(project -> new ProjectView(
                        project.getId(),
                        project.getTitle(),
                        project.getDescription(),
                        project.getFileName(),
                        project.getUser().getUsername()
                ))
                .collect(Collectors.toList());
    }
}
