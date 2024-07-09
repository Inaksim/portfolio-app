package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.form.SignInForm;
import com.portfolioapp.portfolio.app.dto.form.SignUpForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    String saveUser(SignUpForm form);

    ResponseEntity<UserView> signIn(SignInForm form);
}
