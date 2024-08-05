package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.dto.form.*;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpForm form) throws Exception {
        return authenticationService.saveUser(form);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserView> signIn(@RequestBody SignInForm form) {
        return authenticationService.signIn(form);
    }

    @PutMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordForm form) {
        authenticationService.resetPassword(form);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser() {
        return authenticationService.logoutUser();
    }

    @PostMapping("/send-verification-code")
    public void verifyEmail(@RequestBody SendVerificationCodeForm form) {
        authenticationService.sendVerificationCode(form);
    }

    @PostMapping("/check-verification-code")
    public void verifyEmail(@RequestBody CheckVerificationCodeForm form) throws Exception {
        authenticationService.checkVerificationCode(form);
    }
}
