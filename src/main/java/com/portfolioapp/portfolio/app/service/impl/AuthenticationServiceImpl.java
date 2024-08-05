package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.form.*;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.security.Role;
import com.portfolioapp.portfolio.app.security.UserPrincipal;
import com.portfolioapp.portfolio.app.security.jwt.JwtUtils;
import com.portfolioapp.portfolio.app.service.AuthenticationService;
import com.portfolioapp.portfolio.app.service.EmailService;
import exception.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.portfolioapp.portfolio.app.utils.Constraints.EMAIL;
import static com.portfolioapp.portfolio.app.utils.Utils.generateVerificationCode;
import static exception.Errors.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    private FileStorageServiceImpl fileStorageService;

    private EmailService emailService;

    @Override
    public String saveUser(SignUpForm form) throws Exception {
        if(userRepository.existsByEmail(form.getEmail())) {
            throw new ApplicationException(USER_ALREADY_EXIST, Collections.singletonMap(EMAIL, form.getEmail()));
        }

        int code = generateVerificationCode();
        emailService.sendEmail(form.getEmail(),"VERIFICATION CODE", "Your verification code is: " + code);

        User newUser = User.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
                .avatar(fileStorageService.storeFile(form.getAvatar()))
                .bio(form.getBio())
                .banned(false)
                .verificationCode(String.valueOf(code))
//                .socialLinks(form.getSocialLinks())
                .build();


        User user = userRepository.saveAndFlush(newUser);
        return "200";
    }

    @Override
    public ResponseEntity<UserView> signIn(SignInForm form) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getEmail(),
                        form.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userPrincipal);

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserView userView = UserView.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getUsername())
                .username(userPrincipal.getUser_name())
                .role(roles.get(0))
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userView);
    }

    @Override
    public void resetPassword(ResetPasswordForm form) {
        User user = userRepository.findByEmail(form.getEmail()).orElseThrow();
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.saveAndFlush(user);
    }


    @Override
    public ResponseEntity<Void> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @Override
    public void sendVerificationCode(SendVerificationCodeForm form) {
        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, form.getEmail())));
        int code = generateVerificationCode();
        emailService.sendEmail(user.getEmail(), "VerificationCode", "Your verification code is: " + code);
        user.setVerificationCode(String.valueOf(code));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void checkVerificationCode(CheckVerificationCodeForm form) throws Exception {
        User user = userRepository.findByEmail(form.getEmail()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, form.getEmail())));
        if (!user.getVerificationCode().equals(form.getVerificationCode())) {
            throw new ApplicationException(VERIFICATION_CODE_NOT_MATCH);
        }
    }
}
