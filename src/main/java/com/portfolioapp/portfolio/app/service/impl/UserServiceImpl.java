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
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, principal.getName())));
        return convertToUserView(user);
    }

    @Override
    public UserView updateUser(UpdateUserForm form) throws IOException {
        User user = userRepository.findById(form.getId()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, form.getId())));

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
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    @Override
    public UserView getUserView(Long id) {
        User user = userRepository.findById(id).orElseThrow();
       return convertToUserView(user);

    }



    @Override
    public void removeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, userId)));
        userRepository.delete(user);
    }

    private UserView convertToUserView(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setFirstName(user.getFirstName());
        userView.setUsername(user.getUsername());
        userView.setEmail(user.getEmail());
        userView.setFollowingCount(user.getFollowing().size());
        userView.setFollowersCount(user.getFollowers().size());
        userView.setAvatar(user.getAvatar());
        userView.setSocialLinks(user.getSocialLinks());
        userView.setBio(user.getBio());
        return userView;

    }

}
