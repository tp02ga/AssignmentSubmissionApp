package com.coderscampus.AssignmentSubmissionApp.dto;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentStatusEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.JavaFoundationsAssignmentEnum;

public class JavaFoundationsAssignmentResponseDto implements AssignmentResponseDto {
    private Assignment assignment;
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();
    private JavaFoundationsAssignmentEnum[] javaFoundationsAssignmentEnums = JavaFoundationsAssignmentEnum.values();

    public JavaFoundationsAssignmentResponseDto(Assignment assignment) {
        super();
        this.assignment = assignment;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public AssignmentEnum[] getAssignmentEnums() {
        return javaFoundationsAssignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }

}
