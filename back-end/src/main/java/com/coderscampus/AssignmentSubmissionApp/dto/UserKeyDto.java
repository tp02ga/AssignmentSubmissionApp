package com.coderscampus.AssignmentSubmissionApp.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Objects;

public class UserKeyDto implements Comparable<UserKeyDto> {
    private String email;
    private String name;
    private LocalDate startDate;
    private Integer bootcampDurationInWeeks;

    public UserKeyDto(String email,
                      String name,
                      LocalDate startDate,
                      Integer bootcampDurationInWeeks) {
        this.email = email;
        this.name = name;
        this.startDate = startDate;
        this.bootcampDurationInWeeks = bootcampDurationInWeeks;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getBootcampDurationInWeeks() {
        return bootcampDurationInWeeks;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setBootcampDurationInWeeks(Integer bootcampDurationInWeeks) {
        this.bootcampDurationInWeeks = bootcampDurationInWeeks;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();

        module.addSerializer(LocalDateSerializer.INSTANCE);
        mapper.registerModule(module);
        try {
            String jsonString = mapper.writeValueAsString(this);
            return jsonString;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKeyDto that = (UserKeyDto) o;
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public int compareTo(UserKeyDto that) {
        if (this.getStartDate() == null)
            throw new IllegalStateException("User " + this.getEmail() + " has no cohort start date.");
        if (that.getStartDate() == null)
            throw new IllegalStateException("User " + that.getEmail() + " has no cohort start date.");
        if (this.getStartDate().compareTo(that.getStartDate()) == 0) {
            return this.getEmail().compareTo(that.getEmail());
        } else {
            return that.getStartDate().compareTo(this.getStartDate());
        }
    }
}
