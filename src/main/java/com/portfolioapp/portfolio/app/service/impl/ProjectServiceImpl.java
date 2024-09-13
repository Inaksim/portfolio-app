package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.form.NewProjectForm;
import com.portfolioapp.portfolio.app.dto.form.UpdateProjectForm;

import com.portfolioapp.portfolio.app.dto.view.LikeView;
import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.enitity.*;
import com.portfolioapp.portfolio.app.repository.ProjectRepository;

import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.service.ImgurService;
import com.portfolioapp.portfolio.app.service.ProjectService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {


   private UserRepository userRepository;
   private ProjectRepository projectRepository;
   private ModelMapper modelMapper;

   private ImgurService imgurService;

    public ResponseEntity<String> createProject(NewProjectForm form, Principal principal) throws Exception {
        String cover = imgurService.uploadImage(form.getCover());
        Project project = new Project();
        project.setTitle(form.getTitle());
        project.setCover(cover);
        project.setUser(userRepository.findByEmail(principal.getName()).orElseThrow());
        project.setCreatedAt(LocalDateTime.now());
        project.setContent(form.getContent());
        projectRepository.saveAndFlush(project);
        return ResponseEntity.ok("Created");
    }


    @Override
    public ResponseEntity<List<ProjectView>> getAllProjects(int page, int size, Principal principal) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<Project> projectsPage = projectRepository.findAll(pageable);
        List<ProjectView> projectViews = projectsPage.getContent().stream()
                .map(ProjectView::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(projectViews);

    }

    @Override
    public List<ProjectView> getMyProjects(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Project> projects = projectRepository.findProjectsByUser(user);
        return projects.stream()
                .map(ProjectView::new)
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
            project.setCover(form.getCover());
            project.setContent(form.getContent());
            project.setUpdatedAt(LocalDateTime.now());
            Project result = projectRepository.saveAndFlush(project);
            return modelMapper.map(result, ProjectView.class);
        }

        return modelMapper.map(project, ProjectView.class);

    }

    @Override
    public List<ProjectView> searchProjects(String keyword, Principal principal) {
        List<Project> projects = projectRepository.findByTitleContainingIgnoreCase(keyword);

        String username = principal.getName();

        return projects.stream()
                .map(project -> {
                    ProjectView projectView = new ProjectView(project);

                    boolean userHasLiked = project.getLikes().stream()
                            .anyMatch(like -> like.getUser().getEmail().equals(username));

                    projectView.setUserHasLiked(userHasLiked);
                    return projectView;
                })
                .collect(Collectors.toList());
    }



    @Override
    public ProjectView getProjectById(Long projectId, Principal principal) {
        Project project = projectRepository.findProjectById(projectId);
        ProjectView projectView = new ProjectView(project);


        boolean userHasLiked = project.getLikes().stream()
                .anyMatch(like -> like.getUser().getEmail().equals(principal.getName()));

        projectView.setUserHasLiked(userHasLiked);

        return projectView;
    }

    @Override
    public List<ProjectView> getTopProjects(int limit, Principal principal) {
        Pageable pageable = PageRequest.of(0, limit);
        Page<Project> projectsPage = projectRepository.findTopProjectsByLikes(pageable);
        return projectsPage.getContent().stream()
                .map(ProjectView::new)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProjectView> getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findProjectsByUserId(userId);
        return  projects.stream()
                .map(ProjectView::new)
                .collect(Collectors.toList());
    }
}
