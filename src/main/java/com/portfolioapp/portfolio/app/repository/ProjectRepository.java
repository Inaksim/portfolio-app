package com.portfolioapp.portfolio.app.repository;


import com.portfolioapp.portfolio.app.enitity.Project;
import com.portfolioapp.portfolio.app.enitity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByUser(User user);

    Project findProjectById(Long projectId);

    Project getProjectById(Long projectId);

    List<Project> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT p FROM Project p LEFT JOIN p.likes l GROUP BY p.id ORDER BY COUNT(l) DESC")
    Page<Project> findTopProjectsByLikes(Pageable pageable);

    List<Project> findProjectsByUserId(Long userId);
}
