package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.dto.form.SignInForm;
import com.portfolioapp.portfolio.app.dto.form.SignUpForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;


    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpForm form) {
        return authenticationService.saveUser(form);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserView> signIn(@RequestBody SignInForm form) {
        return authenticationService.signIn(form);
    }
}
