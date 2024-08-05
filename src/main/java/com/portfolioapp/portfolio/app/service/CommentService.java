package com.portfolioapp.portfolio.app.service;

import com.portfolioapp.portfolio.app.dto.view.CommentView;
import com.portfolioapp.portfolio.app.enitity.Comment;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    ResponseEntity<?> addComment(Long projectId, String content, Principal principal);

    ResponseEntity<?> deleteComment(Long commentId, Principal principal);

    ResponseEntity<List<CommentView>> getCommentsByProject(Long projectId);
}
