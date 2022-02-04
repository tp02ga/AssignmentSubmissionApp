package com.coderscampus.AssignmentSubmissionApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coderscampus.AssignmentSubmissionApp.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    @Query("select c from Comment c "
            + " where c.assignment.id = :assignmentId")
    Set<Comment> findByAssignmentId(Long assignmentId);

}
