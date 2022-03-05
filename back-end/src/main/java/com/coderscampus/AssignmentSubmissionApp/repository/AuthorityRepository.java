package com.coderscampus.AssignmentSubmissionApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.AssignmentSubmissionApp.domain.Authorities;

public interface AuthorityRepository extends JpaRepository<Authorities, Long>{

}
