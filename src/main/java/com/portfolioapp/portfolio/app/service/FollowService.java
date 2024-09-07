package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.view.UserView;
import org.springframework.http.ResponseEntity;


import java.security.Principal;
import java.util.List;

public interface FollowService {

    void follow(Long userId, Principal principal);


    void unfollow(Long userId, Principal principal);

    ResponseEntity<List<UserView>> getFollowersList(Principal principal);

    ResponseEntity<List<UserView>> getFollowingsList(Principal principal);

    boolean isFollowing(Long userId, Principal principal);
}
