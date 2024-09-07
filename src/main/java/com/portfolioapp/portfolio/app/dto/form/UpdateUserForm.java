package com.portfolioapp.portfolio.app.dto.form;

import lombok.Getter;
import lombok.Setter;


import java.util.Map;

@Getter
@Setter
public class UpdateUserForm {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String role;
    private String bio;
    private String avatar;
    private Map<String, String> socialLinks;
}
