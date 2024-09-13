package com.portfolioapp.portfolio.app.controller;


import com.portfolioapp.portfolio.app.dto.form.UpdateUserForm;
import com.portfolioapp.portfolio.app.dto.view.UserView;
import com.portfolioapp.portfolio.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PutMapping("/update")
    public UserView updateUser(@RequestBody UpdateUserForm form) throws IOException {
        return userService.updateUser(form);
    }
    @GetMapping("/profile/me")
    public UserView myProfile(Principal principal) {
        return userService.getUserProfile(principal);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
         userService.removeUser(userId);
    }

    @GetMapping("/{userId}")
    public UserView getUser(@PathVariable long userId) {
         return userService.getUserView(userId);
    }

}
