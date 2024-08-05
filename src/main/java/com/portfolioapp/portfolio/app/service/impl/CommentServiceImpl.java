package com.portfolioapp.portfolio.app.service.impl;

import com.portfolioapp.portfolio.app.dto.view.CommentView;
import com.portfolioapp.portfolio.app.enitity.Comment;
import com.portfolioapp.portfolio.app.enitity.Project;
import com.portfolioapp.portfolio.app.enitity.User;
import com.portfolioapp.portfolio.app.repository.CommentRepository;
import com.portfolioapp.portfolio.app.repository.ProjectRepository;
import com.portfolioapp.portfolio.app.repository.UserRepository;
import com.portfolioapp.portfolio.app.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Override
    public ResponseEntity<?> addComment(Long projectId, String content, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Project project = projectRepository.findProjectById(projectId);
        Comment comment = new Comment(user, project, content);
        commentRepository.save(comment);
        return ResponseEntity.ok("Comment added successfully");
    }

    @Override
    public ResponseEntity<?> deleteComment(Long commentId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if(comment.getUser() == user) {
            commentRepository.delete(comment);
            return ResponseEntity.ok("Comment removed successfully");
        }
        return ResponseEntity.ok("Error");
    }

    @Override
    public ResponseEntity<List<CommentView>> getCommentsByProject(Long projectId) {
        List<Comment> comments = commentRepository.findCommentsByProjectId(projectId);
        List<CommentView> result = comments.stream()
                .map(CommentView::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
