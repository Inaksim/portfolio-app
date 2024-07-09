package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.form.SignInForm;
import com.portfolioapp.portfolio.app.dto.form.SignUpForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.security.Role;
import com.portfolioapp.portfolio.app.security.UserPrincipal;
import com.portfolioapp.portfolio.app.security.jwt.JwtUtils;
import com.portfolioapp.portfolio.app.service.AuthenticationService;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public String saveUser(SignUpForm form) {
        User newUser = User.builder()
                .email(form.getEmail())
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
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
                .collect(Collectors.toList());

        UserView userView = UserView.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getUsername())
                .username(userPrincipal.getUser_name())
                .role(userPrincipal.getRole())
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userView);

    }
}
