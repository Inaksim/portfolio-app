package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.dto.view.CommentView;
import com.portfolioapp.portfolio.app.enitity.Comment;
import com.portfolioapp.portfolio.app.service.CommentService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/add/{projectId}")
    public ResponseEntity<?> addComment(@PathVariable Long projectId, @RequestBody String content, Principal principal) {
        return commentService.addComment(projectId, content, principal);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, Principal principal) {
        return commentService.deleteComment(commentId, principal);
    }

    @GetMapping("/get/{projectId}")
    public ResponseEntity<List<CommentView>> getCommentsByProject(@PathVariable Long projectId) {
        return commentService.getCommentsByProject(projectId);
    }
}
