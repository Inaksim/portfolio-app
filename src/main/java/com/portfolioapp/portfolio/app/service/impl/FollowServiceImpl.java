package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.Follower;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.FollowerRepository;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.service.FollowService;
import com.portfolioapp.portfolio.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

    private FollowerRepository followerRepository;
    private UserService userService;
    private UserRepository userRepository;

    @Override
    public void follow(Long userId, Principal principal) {
        User follower = userService.getUserByEmail(principal.getName());
        User followedTo = userService.getUserById(userId);

        if(followerRepository.existsByFollowerAndFollowed(follower, followedTo)) {
            throw new RuntimeException("Already followed");
        }

        Follower newFollow = new Follower(follower, followedTo);
        followerRepository.saveAndFlush(newFollow);

    }

    @Override
    public void unfollow(Long userId, Principal principal) {
        User follower = userService.getUserByEmail(principal.getName());
        User followedTo = userService.getUserById(userId);

        if(!followerRepository.existsByFollowerAndFollowed(follower, followedTo)) {
            throw new RuntimeException("Not found following");
        }

        Follower foll = followerRepository.findByFollowerAndFollowed(follower, followedTo);

        followerRepository.delete(foll);
    }


    //List of following
    @Override
    public ResponseEntity<List<UserView>>  getFollowingsList(Principal principal) {
        User follower = userService.getUserByEmail(principal.getName());
        List<Follower> following = followerRepository.findByFollower(follower);
        List<UserView> followedUsers = following.stream()
                .map(follow -> new UserView(follow.getFollowed()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(followedUsers);
    }

    //List of followers
    @Override
    public ResponseEntity<List<UserView>> getFollowersList(Principal principal) {
        User followed = userService.getUserByEmail(principal.getName());
        List<Follower> followers = followerRepository.findByFollowed(followed);
        List<UserView> followerUsers = followers.stream()
                .map(follow -> new UserView(follow.getFollower()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(followerUsers);
    }

    @Override
    public boolean isFollowing(Long userId, Principal principal) {
        User follower = userRepository.findByEmail(principal.getName()).orElseThrow();
        User followed = userRepository.findById(userId).orElseThrow();

        return followerRepository.existsByFollowerAndFollowed(follower, followed);
    }
}
