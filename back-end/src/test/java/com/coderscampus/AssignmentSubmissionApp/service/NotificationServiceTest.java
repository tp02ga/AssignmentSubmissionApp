package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.domain.User;

class NotificationServiceTest {

    NotificationService sut = new NotificationService();
    
    void test() {
        Assignment testAssignment = new Assignment();
        
        User user = new User();
        user.setName("Trevor Page");
        
        testAssignment.setUser(user);
        testAssignment.setNumber(4);
        sut.sendSlackMessage(testAssignment, "C05UGL8F42H");
        
    }
    
    

}
