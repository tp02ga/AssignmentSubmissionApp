package com.coderscampus.AssignmentSubmissionApp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.repository.UserRepository;
import com.coderscampus.proffesso.domain.ProffessoUser;
import com.coderscampus.proffesso.repository.ProffessoUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProffessoUserRepo proffessoUserRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<ProffessoUser> proffessoUserOpt = proffessoUserRepo.findByEmail(username);
        if (proffessoUserOpt.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        
        Optional<User> userOpt = userRepo.findByUsername(username);
        if (userOpt.isEmpty()) {
            // user exists in Proffesso but not here, this means we'll need to create an account
            //  for this user in this app
            User user = new User(proffessoUserOpt.get());
            user = userRepo.save(user);
            return user;
        } else {
            // TODO: Check that proffesso user and this app's user are in sync.
            return userOpt.get();
        }
        
    }

}
