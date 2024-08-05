package com.portfolioapp.portfolio.app.repository;

import com.portfolioapp.portfolio.app.enitity.Like;
import com.portfolioapp.portfolio.app.enitity.Project;
import com.portfolioapp.portfolio.app.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeRepository  extends JpaRepository<Like, Long> {

    boolean existsByUserAndProject(User user, Project project);

    List<Like> getLikesByUser(User user);

    List<Like> getLikesByProject(Project project);

    Like findByUserAndProject(User user, Project project);
}
