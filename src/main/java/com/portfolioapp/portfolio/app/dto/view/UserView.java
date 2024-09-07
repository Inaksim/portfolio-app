package com.portfolioapp.portfolio.app.dto.view;

import com.portfolioapp.portfolio.app.enitity.User;
import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class UserView {
    private Long id;
    private String firstName;
    private String username;
    private String email;
    private String role;
    private String avatar;
    private Map<String, String> socialLinks;
    private int followersCount;
    private int followingCount;
    private String bio;


    public UserView(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public UserView( ){}
}
