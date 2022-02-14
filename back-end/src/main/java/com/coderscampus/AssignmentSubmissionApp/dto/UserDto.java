package com.coderscampus.AssignmentSubmissionApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
	@JsonProperty("name")
private String name;
	@JsonProperty("username")
private String username;
	@JsonProperty("password")
private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
