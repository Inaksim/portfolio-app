package com.portfolioapp.portfolio.app.service;


import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("AppUserDetailsService")
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email)  {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new UserPrincipal(user);
    }
}
