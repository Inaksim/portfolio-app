package com.portfolioapp.portfolio.app.dto.view;

import com.portfolioapp.portfolio.app.enitity.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeView {
    private Long id;
    private String username;

    public LikeView() {}

    public LikeView(Like like) {
        this.id = like.getId();
        this.username = like.getUser().getUsername();
    }
}
