package com.portfolioapp.portfolio.app.dto.view;

import com.portfolioapp.portfolio.app.enitity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentView {
    private Long id;
    private String username;
    private String content;

    public CommentView(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getCommentText();
    }
}
