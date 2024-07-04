package com.portfolioapp.portfolio.app.repository;

import com.portfolioapp.portfolio.app.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
