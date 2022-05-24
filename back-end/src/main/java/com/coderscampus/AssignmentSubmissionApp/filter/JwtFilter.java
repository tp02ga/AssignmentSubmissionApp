package com.coderscampus.AssignmentSubmissionApp.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.repository.UserRepository;
import com.coderscampus.AssignmentSubmissionApp.util.JwtUtil;
import com.coderscampus.proffesso.repository.ProffessoUserRepo;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    
    
    @Autowired
    private ProffessoUserRepo proffessoUserRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getCookies() == null) {
            chain.doFilter(request, response);
            return;
        }
        // Get authorization header and validate
        Optional<Cookie> jwtOpt = Arrays.stream(request.getCookies())
              .filter(cookie -> "jwt".equals(cookie.getName()))
              .findAny();
        
        if (jwtOpt.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        String token = jwtOpt.get().getValue();
        UserDetails userDetails = null;
        try {
            Optional<User> appUserOpt = userRepo.findByUsername(jwtUtil.getUsernameFromToken(token));
            userDetails = proffessoUserRepo
                    .findByEmail(jwtUtil.getUsernameFromToken(token))
                    .map(proffessoUser -> new User(proffessoUser, appUserOpt))
                    .orElse(null);
        } catch (ExpiredJwtException | SignatureException e) {
            chain.doFilter(request, response);
            return;
        }
        
        // Get jwt token and validate
        if (!jwtUtil.validateToken(token, userDetails)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                    List.of() : userDetails.getAuthorities()
            );

        authentication.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // this is where the authentication magic happens and the user is now valid!
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        chain.doFilter(request, response);
        
    }
    
    
}
