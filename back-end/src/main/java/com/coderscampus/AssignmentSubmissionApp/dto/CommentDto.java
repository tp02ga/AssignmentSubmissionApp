package com.coderscampus.AssignmentSubmissionApp.dto;

import java.time.ZonedDateTime;

public class CommentDto {
    private Long id;
    private Long assignmentId;
    private String text;
    private String user;
    private ZonedDateTime createdDate;

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CommentDto [id=" + id + ", assignmentId=" + assignmentId + ", text=" + text + ", user=" + user
                + ", createdDate=" + createdDate + "]";
    }

}
