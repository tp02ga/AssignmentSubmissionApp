package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import org.junit.jupiter.api.Test;


class NotificationServiceTest {

    NotificationService sut = new NotificationService();
    
    void test() {
        Assignment testAssignment = new Assignment();
        
        User user = new User();
        user.setName("Trevor Page");
        
        testAssignment.setUser(user);
        testAssignment.setNumber(4);
        sut.sendCongratsOnAssignmentSubmissionSlackMessage(testAssignment, "C05UGL8F42H");
        
    }

    // @Test
    void code_reviewer_channel_slack_test () {
        User user = new User();
        user.setName("Trevor Page");
        Assignment testAssignment = new Assignment();

        testAssignment.setUser(user);
        testAssignment.setNumber(4);
        testAssignment.setStatus("SUBMITTED");
        sut.sendAssignmentStatusUpdateCodeReviewer("PENDING_SUBMISSION", testAssignment);
    }
    
    

}
