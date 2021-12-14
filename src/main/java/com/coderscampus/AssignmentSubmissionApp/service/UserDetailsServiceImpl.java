package com.coderscampus.AssignmentSubmissionApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.util.CustomPasswordEncoder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomPasswordEncoder passwordEncoder;    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.getPasswordEncoder().encode("asdfasdf"));
        user.setId(1L);
        return user;
        
    }

}
