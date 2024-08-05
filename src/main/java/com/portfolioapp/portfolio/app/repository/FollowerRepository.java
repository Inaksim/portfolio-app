package com.portfolioapp.portfolio.app.repository;

import com.portfolioapp.portfolio.app.enitity.Follower;
import com.portfolioapp.portfolio.app.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

    boolean existsByFollowerAndFollowed(User follower, User followedTo);

    Follower findByFollowerAndFollowed(User follower, User followedTo);

    List<Follower> findByFollower(User follower);

    List<Follower> findByFollowed(User following);


}
