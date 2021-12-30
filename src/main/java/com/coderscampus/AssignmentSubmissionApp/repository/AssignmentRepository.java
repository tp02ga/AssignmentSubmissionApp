package com.coderscampus.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

}
