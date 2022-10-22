package com.coderscampus.AssignmentSubmissionApp.dto;

import com.coderscampus.AssignmentSubmissionApp.domain.Assignment;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.AssignmentStatusEnum;
import com.coderscampus.AssignmentSubmissionApp.enums.BootcampAssignmentEnum;

public class BootcampAssignmentResponseDto implements AssignmentResponseDto {

    private Assignment assignment;
    private BootcampAssignmentEnum[] bootcampAssignmentEnums = BootcampAssignmentEnum.values();
    private AssignmentStatusEnum[] statusEnums = AssignmentStatusEnum.values();

    public BootcampAssignmentResponseDto() {
    }

    public BootcampAssignmentResponseDto(Assignment assignment) {
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
        return bootcampAssignmentEnums;
    }

    public AssignmentStatusEnum[] getStatusEnums() {
        return statusEnums;
    }
}
