package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.form.UpdateUserForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.security.Role;
import com.portfolioapp.portfolio.app.service.UserService;
import com.portfolioapp.portfolio.app.exception.ApplicationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;

import static com.portfolioapp.portfolio.app.utils.Constraints.EMAIL;
import static com.portfolioapp.portfolio.app.utils.Constraints.ID;
import static com.portfolioapp.portfolio.app.exception.Errors.USER_NOT_FOUND;
import static com.portfolioapp.portfolio.app.exception.Errors.USER_NOT_FOUND_BY_EMAIL;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;
    @Override
    public UserView getUserProfile(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, principal.getName())));
        return new UserView(user);
    }

    @Override
    public UserView updateUser(UpdateUserForm form) {
        User user = userRepository.findById(form.getId()).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, form.getId())));

        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setRole(Role.USER);
        user.setFirstName(form.getFirstName());
        user.setBio(form.getBio());
        user.setSocialLinks(form.getSocialLinks());
        user.setAvatar(form.getAvatar());

        User result = userRepository.saveAndFlush(user);
        return modelMapper.map(result, UserView.class);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, email)));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(ID, id)));
    }
    @Override
    public UserView getUserView(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(ID, id)));
       return new UserView(user);

    }

    @Override
    public void removeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, userId)));
        userRepository.delete(user);
    }

}
