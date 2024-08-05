package com.portfolioapp.portfolio.app.dto.view;

import com.portfolioapp.portfolio.app.enitity.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserView {
    private Long id;
    private String username;
    private String email;
    private String role;


    public UserView(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
