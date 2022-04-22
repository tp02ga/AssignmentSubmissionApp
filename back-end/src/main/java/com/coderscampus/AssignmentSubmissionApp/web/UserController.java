package com.coderscampus.AssignmentSubmissionApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.UserDto;
import com.coderscampus.AssignmentSubmissionApp.service.UserService;
import com.coderscampus.AssignmentSubmissionApp.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://assignments.cod3rscampus.com"}, allowCredentials = "true")
public class UserController {
	@Autowired
	private UserService userService;
	  @Autowired
	    private AuthenticationManager authenticationManager;     
	    @Autowired
	    private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	private ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
		userService.createUser(userDto);
		
		 try {
	            Authentication authenticate = authenticationManager
	                .authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                        userDto.getUsername(), userDto.getPassword()
	                    )
	                );

	            User user = (User) authenticate.getPrincipal();
	            user.setPassword(null);
	            return ResponseEntity.ok()
	                .header(
	                    HttpHeaders.AUTHORIZATION,
	                    jwtUtil.generateToken(user)
	                )
	                .body(user);
	        } catch (BadCredentialsException ex) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }
	    }
		
	}


