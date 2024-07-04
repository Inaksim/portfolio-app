package com.portfolioapp.portfolio.app.repository;

import com.portfolioapp.portfolio.app.enitity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
