package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.form.*;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import org.springframework.http.ResponseEntity;



public interface AuthenticationService {

    String saveUser(SignUpForm form) throws Exception;

    ResponseEntity<UserView> signIn(SignInForm form);

    void resetPassword(ResetPasswordForm form);

    ResponseEntity<Void> logoutUser();

    void sendVerificationCode(SendVerificationCodeForm form);

    void checkVerificationCode(CheckVerificationCodeForm form) throws Exception;
}
