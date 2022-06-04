package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.Comment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.CommentDto;
import com.coderscampus.AssignmentSubmissionApp.repository.AssignmentRepository;
import com.coderscampus.AssignmentSubmissionApp.repository.CommentRepository;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;

@Service
public class CommentService {
    private Logger log = LoggerFactory.getLogger(CommentService.class);

    private CommentRepository commentRepo;
    private AssignmentRepository assignmentRepo;
    private NotificationService notificationService;

    @Value("${domainRoot}")
    private String domainRoot;

    public CommentService(CommentRepository commentRepo, AssignmentRepository assignmentRepo, NotificationService notificationService) {
        this.commentRepo = commentRepo;
        this.assignmentRepo = assignmentRepo;
        this.notificationService = notificationService;
    }

    public Comment save(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        Assignment assignment = assignmentRepo.getById(commentDto.getAssignmentId());
        
        comment.setId(commentDto.getId());
        comment.setAssignment(assignment);
        comment.setText(commentDto.getText());
        comment.setCreatedBy(user);
        if (comment.getId() == null)
            comment.setCreatedDate(ZonedDateTime.now());
        else
            comment.setCreatedDate(commentDto.getCreatedDate());

        Comment savedComment = commentRepo.save(comment);
        User recipient = getCommentRecipient(savedComment);
        if (recipient != null) {
            String message = "There's a new comment on Assignment #" + assignment.getNumber() + "\n\n" + savedComment.getCreatedBy().getName() + ": " + savedComment.getText();
            String htmlMessage = message + "\n\n<a href='"+domainRoot+"/assignments/"+assignment.getId()+"'>Click Here to View the Assignment</a>";
            try {
                notificationService.sendEmail(recipient.getUsername(), htmlMessage, message, "New Comment on Assignment");
            } catch (JSONException e) {
                log.error("Failed to send email notification for new comment", e);
            }
        }

        return savedComment;
    }

    private User getCommentRecipient(Comment comment) {
        User createdBy = comment.getCreatedBy();
        User assignmentOwner = comment.getAssignment().getUser();

        if (createdBy.equals(assignmentOwner)) {
            return comment.getAssignment().getCodeReviewer();
        } else {
            return assignmentOwner;
        }
    }

    public Set<Comment> getCommentsByAssignmentId(Long assignmentId) {
        Set<Comment> comments = commentRepo.findByAssignmentId(assignmentId);
        
        return comments;
    }

    public void delete(Long commentId) {
        commentRepo.deleteById(commentId);
        
    }

    
}
