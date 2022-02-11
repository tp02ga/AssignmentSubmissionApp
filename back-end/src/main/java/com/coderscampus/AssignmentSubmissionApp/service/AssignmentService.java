package com.coderscampus.AssignmentSubmissionApp.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentStatusEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.AuthorityEnum;
import com.coderscampus.AssignmentSubmissionApp.repository.AssignmentRepository;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;

    public Assignment save(User user) {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatusEnum.PENDING_SUBMISSION.getStatus());
        assignment.setNumber(findNextAssignmentToSubmit(user));
        assignment.setUser(user);

        return assignmentRepo.save(assignment);
    }

    private Integer findNextAssignmentToSubmit(User user) {
        Set<Assignment> assignmentsByUser = assignmentRepo.findByUser(user);
        if (assignmentsByUser == null) {
            return 1;
        }
        Optional<Integer> nextAssignmentNumOpt = assignmentsByUser.stream()
                .sorted((a1, a2) -> {
                    if (a1.getNumber() == null)
                        return 1;
                    if (a2.getNumber() == null)
                        return 1;
                    return a2.getNumber().compareTo(a1.getNumber());
                })
                .map(assignment -> {
                    if (assignment.getNumber() == null)
                        return 1;
                    return assignment.getNumber() + 1;
                })
                .findFirst();
        return nextAssignmentNumOpt.orElse(1);
    }

    public Set<Assignment> findByUser(User user) {
        boolean hasCodeReviewerRole = user.getAuthorities()
            .stream()
            .filter(auth -> AuthorityEnum.ROLE_CODE_REVIEWER.name().equals(auth.getAuthority()))
            .count() > 0;
        if (hasCodeReviewerRole) {
            // load assignments if you're a code reviewer role
            return assignmentRepo.findByCodeReviewer(user);
        } else {
            // load assignments if you're a student role
            return assignmentRepo.findByUser(user);
        }
    }

    public Optional<Assignment> findById(Long assignmentId) {
        return assignmentRepo.findById(assignmentId);
    }

    public Assignment save(Assignment assignment) {
    	//load the assignment from DB using assignment.getId() in order to get current status
    	
    	//compare old status from above to the new status "assignment.getStatus()" to determine if an email should be sent
    	
    	
    	//if changing from PENDING_SUBMISSION to SUBMITTED, then email code reviewers
    	
    	//if changing from NEEDS_UPDATE to RESUBMITTED, then email code reviewers
    	
    	//if changing from IN_REVIEW to COMPLETED, then email student
    	
    	//if changing from IN_REVIEW to NEEDS_UPDATES, then email student
        return assignmentRepo.save(assignment);
    }
}
