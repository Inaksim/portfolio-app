package com.portfolioapp.portfolio.app.controller;


import com.portfolioapp.portfolio.app.dto.view.ProjectView;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private LikeService likeService;

    @PostMapping("/like/{projectId}")
    public ResponseEntity<?> likeProject(@PathVariable Long projectId, Principal principal) {
        return likeService.likeProject(projectId, principal);
    }

    @DeleteMapping("/unlike/{projectId}")
    public ResponseEntity<?> unlikeProject(@PathVariable Long projectId, Principal principal) {
        return likeService.unlikeProject(projectId, principal);
    }


    @GetMapping("/liked")
    public List<ProjectView> getLikedProjectsByUser(Principal principal) {
        return likeService.getLikedProjects(principal);
    }

    @GetMapping("/project/{projectId}")
    public List<UserView> getLikesByProject(@PathVariable Long projectId) {
        return likeService.getLikesByProject(projectId);
    }

    @GetMapping("/projects/{userId}")
    public List<ProjectView> getLikedProjectsByUserId(@PathVariable Long userId) {
        return likeService.getLikedProjectsByUserId(userId);
    }

}
