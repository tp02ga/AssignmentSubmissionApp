package com.coderscampus.AssignmentSubmissionApp.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.AssignmentSubmissionApp.domain.Comment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.CommentDto;
import com.coderscampus.AssignmentSubmissionApp.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @PostMapping("")
    public ResponseEntity<Comment> createComment (@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
        Comment comment = commentService.save(commentDto, user);
        
        return ResponseEntity.ok(comment);
    }
    
    @GetMapping("")
    public ResponseEntity<Set<Comment>> getCommentsByAssignment(@RequestParam Long assignmentId) {
        Set<Comment> comments = commentService.getCommentsByAssignmentId(assignmentId);
        
        return ResponseEntity.ok(comments);
    }
    
}
