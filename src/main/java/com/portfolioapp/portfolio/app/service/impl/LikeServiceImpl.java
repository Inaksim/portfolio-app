package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.Like;
import com.portfolioapp.portfolio.app.enitity.Project;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.LikeRepository;
import com.portfolioapp.portfolio.app.repository.ProjectRepository;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;


    @Override
    public ResponseEntity<?> likeProject(Long projectId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Project project = projectRepository.findProjectById(projectId);
        if(likeRepository.existsByUserAndProject(user, project)) {
            throw new RuntimeException("Project already liked by user");
        }
        Like like = new Like(user, project);
        likeRepository.save(like);
        return ResponseEntity.ok("Project liked successfully");
    }

    @Override
    public ResponseEntity<?> unlikeProject(Long projectId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Project project = projectRepository.findProjectById(projectId);
        if(!likeRepository.existsByUserAndProject(user, project)) {
           throw new RuntimeException("Like not found");
        }
        Like like = likeRepository.findByUserAndProject(user, project);
        likeRepository.delete(like);
        return ResponseEntity.ok("Project unliked successfully");
    }

    @Override
    public List<ProjectView> getLikedProjects(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<Like> likes = likeRepository.getLikesByUser(user);
        return likes.stream()
                .map(like -> new ProjectView(like.getProject()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserView> getLikesByProject(Long projectId) {
        Project project = projectRepository.getProjectById(projectId);
        List<Like> likes = likeRepository.getLikesByProject(project);
        return likes.stream()
                .map(like -> new UserView(like.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectView> getLikedProjectsByUserId(Long userId) {
        List<Like> likes = likeRepository.findByUserId(userId);
        return likes.stream()
                .map(like -> new ProjectView(like.getProject()))
                .collect(Collectors.toList());

    }
}
