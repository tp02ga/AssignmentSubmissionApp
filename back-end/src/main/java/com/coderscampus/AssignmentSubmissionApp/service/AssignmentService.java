package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.UserKeyDto;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentStatusEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.AuthorityEnum;
import com.coderscampus.AssignmentSubmissionApp.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepo;
    @Autowired
    private NotificationService notificationService;

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
        Optional<Integer> nextAssignmentNumOpt = assignmentsByUser.stream().sorted((a1, a2) -> {
            if (a1.getNumber() == null)
                return 1;
            if (a2.getNumber() == null)
                return 1;
            return a2.getNumber().compareTo(a1.getNumber());
        }).map(assignment -> {
            if (assignment.getNumber() == null)
                return 1;
            return assignment.getNumber() + 1;
        }).findFirst();
        return nextAssignmentNumOpt.orElse(1);
    }

    @Secured({"ROLE_INSTRUCTOR"})
    public Map<UserKeyDto, Set<Assignment>> findAll() {
        List<Assignment> assignments = assignmentRepo.findAllActiveBootcampStudents();

        Map<UserKeyDto, Set<Assignment>> allStudentAssignments = assignments.stream()
                .collect(Collectors.groupingBy(a -> new UserKeyDto(a.getUser().getUsername(),
                                a.getUser().getName(),
                                a.getUser().getCohortStartDate(),
                                a.getUser().getBootcampDurationInWeeks()),
                        Collectors.mapping(a -> a, Collectors.toSet())));

        TreeMap<UserKeyDto, Set<Assignment>> sortedMap = new TreeMap<>(allStudentAssignments);
        return sortedMap;
    }

    public Set<Assignment> findByUser(User user) {
        boolean hasCodeReviewerRole = user.getAuthorities().stream()
                .filter(auth -> AuthorityEnum.ROLE_CODE_REVIEWER.name().equals(auth.getAuthority())).count() > 0;
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
        // load the assignment from DB using assignment.getId() in order to get current
        // status
        Assignment oldAssignment = assignmentRepo.findById(assignment.getId()).get();
        String oldStatus = oldAssignment.getStatus();

        Assignment newAssignment = assignmentRepo.save(assignment);
        String newStatus = newAssignment.getStatus();

        if (!oldStatus.equals(newStatus)) {
            if ((oldStatus.contentEquals("Pending Submission") && newStatus.contentEquals("Submitted"))) {
                newAssignment.setSubmittedDate(LocalDateTime.now());
                newAssignment = assignmentRepo.save(newAssignment);
            }
            if ((oldStatus.contentEquals("Pending Submission") && newStatus.contentEquals("Submitted"))
                    || (oldStatus.contentEquals("Needs Update") && newStatus.contentEquals("Resubmitted"))) {
                notificationService.sendAssignmentStatusUpdateCodeReviewer(oldStatus, assignment);
            }

            if ((oldStatus.contentEquals("In Review") && newStatus.contentEquals("Completed"))
                    || (oldStatus.equals("In Review") && newStatus.equals("Needs Update"))) {
                notificationService.sendAssignmentStatusUpdateStudent(oldStatus, assignment);
            }

        }

        return newAssignment;
    }
}
