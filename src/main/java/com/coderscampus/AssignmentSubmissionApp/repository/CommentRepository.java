package com.coderscampus.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.AssignmentSubmissionApp.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
