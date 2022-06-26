package com.coderscampus.AssignmentSubmissionApp.web;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.UserDto;
import com.coderscampus.AssignmentSubmissionApp.dto.UserKeyDto;
import com.coderscampus.AssignmentSubmissionApp.service.UserService;
import com.coderscampus.AssignmentSubmissionApp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://assignments.coderscampus.com"}, allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        Optional<User> userByUsername = userService.findUserByUsername(email);
        
        return ResponseEntity.ok(userByUsername);
    }

    @PutMapping("{email}")
    @Secured({"ROLE_INSTRUCTOR"})
    public ResponseEntity<?> saveUser(@RequestBody UserKeyDto user) {
        System.out.println("Got user: " + user);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

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

    @GetMapping("/non-configured")
    public ResponseEntity<?> nonConfiguredStudents() {
        List<User> nonConfiguredStudents = userService.findNonConfiguredStudents();
        List<UserKeyDto> result = nonConfiguredStudents.stream()
                .map(u -> new UserKeyDto(u.getUsername(), u.getName(), u.getCohortStartDate(), u.getBootcampDurationInWeeks()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/bootcamp-students")
    public ResponseEntity<?> getBootcampStudents() {
        List<UserKeyDto> bootcampStudents = userService.findBootcampStudents();

        return ResponseEntity.ok(bootcampStudents);
    }
}


