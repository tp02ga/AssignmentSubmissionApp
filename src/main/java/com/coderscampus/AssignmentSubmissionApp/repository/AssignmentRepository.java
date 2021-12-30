package com.coderscampus.AssignmentSubmissionApp.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

    Set<Assignment> findByUser(User user);
}
