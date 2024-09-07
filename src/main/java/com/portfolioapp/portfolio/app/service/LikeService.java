package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface LikeService {


    ResponseEntity<?> likeProject(Long projectId, Principal principal);

    ResponseEntity<?> unlikeProject(Long projectId, Principal principal);

    List<ProjectView> getLikedProjects(Principal principal);

    List<UserView> getLikesByProject(Long projectId);

    List<ProjectView> getLikedProjectsByUserId(Long userId);
}
