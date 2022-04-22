package com.coderscampus.AssignmentSubmissionApp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.AuthCredentialsRequest;
import com.coderscampus.AssignmentSubmissionApp.util.JwtUtil;

import ch.qos.logback.core.util.Duration;
import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://assignments.cod3rscampus.com"}, allowCredentials = "true")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Value("${cookies.domain}")
    private String domain;
    @Value("${cookies.protocol")
    private String protocol;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            user.setPassword(null);

            String token = jwtUtil.generateToken(user);
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
//                    .domain(domain)
//                    .httpOnly(true)
//                    .secure(true)
                    .path("/")
                    .maxAge(Duration.buildByDays(365).getMilliseconds())
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt") String token,
            @AuthenticationPrincipal User user) {
        try {
            Boolean isValidToken = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }
}
