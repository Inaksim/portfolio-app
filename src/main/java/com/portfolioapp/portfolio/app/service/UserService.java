package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.form.UpdateUserForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.enitity.User;

import java.io.IOException;
import java.security.Principal;

public interface UserService {

    UserView getUserProfile(Principal principal);

    UserView updateUser(UpdateUserForm form) throws IOException;

    User getUserByEmail(String email);
    User getUserById(Long id);

    UserView getUserView(Long id);

    void removeUser(Long userId);
}
