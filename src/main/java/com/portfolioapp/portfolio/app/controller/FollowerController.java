package com.portfolioapp.portfolio.app.controller;


import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/follow")
public class FollowerController {



   private FollowService followService;


    /*
    userId - user you want to subscribe to
     */

    @PostMapping("/new-follow/{userId}")
    public String follow(@PathVariable Long userId, Principal principal) {
        followService.follow(userId, principal);
        return "Success";
    }



    @PostMapping("/unfollow/{userId}")
    public String unfollow(@PathVariable Long userId, Principal principal) {
        followService.unfollow(userId, principal);
        return "Success";
    }

    @GetMapping("/followers-list")
    public ResponseEntity<List<UserView>> getFollowers(Principal principal){
        return followService.getFollowersList(principal);
    }

    @GetMapping("/following-list")
    public ResponseEntity<List<UserView>> getFollowing(Principal principal) {
        return followService.getFollowingsList(principal);

    }
}
