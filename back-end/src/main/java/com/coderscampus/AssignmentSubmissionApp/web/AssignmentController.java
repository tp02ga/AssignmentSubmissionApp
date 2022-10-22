package com.coderscampus.AssignmentSubmissionApp.web;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.AssignmentResponseDto;
import com.coderscampus.AssignmentSubmissionApp.dto.BootcampAssignmentResponseDto;
import com.coderscampus.AssignmentSubmissionApp.dto.JavaFoundationsAssignmentResponseDto;
import com.coderscampus.AssignmentSubmissionApp.dto.UserKeyDto;
import com.coderscampus.AssignmentSubmissionApp.enums.AuthorityEnum;
import com.coderscampus.AssignmentSubmissionApp.service.AssignmentService;
import com.coderscampus.AssignmentSubmissionApp.service.OrderService;
import com.coderscampus.AssignmentSubmissionApp.service.UserService;
import com.coderscampus.AssignmentSubmissionApp.util.AuthorityUtil;
import com.coderscampus.proffesso.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.coderscampus.AssignmentSubmissionApp.service.OrderService.BOOTCAMP_OFFER_ID;
import static com.coderscampus.AssignmentSubmissionApp.service.OrderService.JAVA_FOUNDATIONS_OFFER_ID;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://assignments.coderscampus.com"}, allowCredentials = "true")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createAssignment(@AuthenticationPrincipal User user) {
        Assignment newAssignment = assignmentService.save(user);

        return ResponseEntity.ok(newAssignment);
    }

    @GetMapping("")
    public ResponseEntity<?> getAssignments(@AuthenticationPrincipal User user) {
        Set<Assignment> assignmentsByUser = assignmentService.findByUser(user);
        return ResponseEntity.ok(assignmentsByUser);
    }

    @GetMapping("{assignmentId}")
    public ResponseEntity<?> getAssignment(@PathVariable Long assignmentId, @AuthenticationPrincipal User user) {
        Optional<Assignment> assignmentOpt = assignmentService.findById(assignmentId);

        Set<Offer> offers = orderService.findStudentOrdersByUserId(user.getId());
        boolean isBootcampStudent = offers.stream()
                .anyMatch(offer -> offer.getId().equals(BOOTCAMP_OFFER_ID));
        boolean isJavaFoundationsStudent = offers.stream()
                .anyMatch(offer -> offer.getId().equals(JAVA_FOUNDATIONS_OFFER_ID));

        if (isBootcampStudent) {

            AssignmentResponseDto response = new BootcampAssignmentResponseDto(assignmentOpt.orElse(new Assignment()));
            return ResponseEntity.ok(response);
        } else if (isJavaFoundationsStudent) {
            AssignmentResponseDto response = new JavaFoundationsAssignmentResponseDto(assignmentOpt.orElse(new Assignment()));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(new BootcampAssignmentResponseDto());
    }

    @PutMapping("{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Long assignmentId,
                                              @RequestBody Assignment assignment,
                                              @AuthenticationPrincipal User user) {
        // add the code reviewer to this assignment if it was claimed
        if (assignment.getCodeReviewer() != null) {
            User codeReviewer = assignment.getCodeReviewer();
            codeReviewer = userService.findUserByUsername(codeReviewer.getUsername()).orElseThrow();

            if (AuthorityUtil.hasRole(AuthorityEnum.ROLE_CODE_REVIEWER.name(), codeReviewer)) {
                assignment.setCodeReviewer(codeReviewer);
            }
        }
        Assignment updatedAssignment = assignmentService.save(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allAssignments() {
        Map<UserKeyDto, Set<Assignment>> allAssignments = assignmentService.findAll();
        return ResponseEntity.ok(allAssignments);
    }


}
