package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.AssignmentSubmissionApp.domain.Authorities;
import com.coderscampus.AssignmentSubmissionApp.domain.User;
import com.coderscampus.AssignmentSubmissionApp.dto.UserDto;
import com.coderscampus.AssignmentSubmissionApp.dto.UserKeyDto;
import com.coderscampus.AssignmentSubmissionApp.repository.AuthorityRepository;
import com.coderscampus.AssignmentSubmissionApp.repository.UserRepository;
import com.coderscampus.AssignmentSubmissionApp.util.CustomPasswordEncoder;
import com.coderscampus.proffesso.domain.ProffessoUser;
import com.coderscampus.proffesso.repository.ProffessoUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProffessoUserRepo proffessoUserRepo;
    @Autowired
    private AuthorityRepository authorityRepo;
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void createUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setName(userDto.getName());
        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(userDto.getPassword());
        newUser.setPassword(encodedPassword);
        userRepo.save(newUser);
        Authorities authority = new Authorities();
        authority.setAuthority("ROLE_STUDENT");
        authority.setUser(newUser);
        authorityRepo.save(authority);

    }

    public User duplicateProffessoUser(ProffessoUser proffessoUser) {
        User user = new User(proffessoUser, Optional.empty());
        user = userRepo.save(user);
        return user;
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @Transactional
    public List<UserKeyDto> findBootcampStudents() {
        List<Tuple> proffessoUsersRawData = proffessoUserRepo.findBootcampStudents();
        List<User> users = userRepo.findAll();
        List<UserKeyDto> proffessoUsers = proffessoUsersRawData.stream()
                .map(data -> {
                    Optional<User> match = users.stream().filter(user -> user.getUsername().equals(data.get(0))).findFirst();
                    UserKeyDto userKeyDto = new UserKeyDto((String) data.get(0), (String) data.get(1), null, null);
                    match.ifPresent(user -> {
                        userKeyDto.setBootcampDurationInWeeks(user.getBootcampDurationInWeeks());
                        userKeyDto.setStartDate(user.getCohortStartDate());
                    });
                    return userKeyDto;
                })
                .collect(Collectors.toList());

        return proffessoUsers;
    }

    @Secured({"ROLE_INSTRUCTOR"})
    public List<User> findNonConfiguredStudents() {
        return userRepo.findAllInactiveBootcampStudents();
    }

    public void updateUser(UserKeyDto user) {
        Optional<User> userOpt = userRepo.findByUsername(user.getEmail());
        userOpt.ifPresentOrElse(u -> {
            u.setCohortStartDate(user.getStartDate() != null ? user.getStartDate() : user.getCohortStartDate());
            u.setBootcampDurationInWeeks(user.getBootcampDurationInWeeks());
            u.setName(user.getName());
            userRepo.save(u);
        }, () -> {
            Optional<ProffessoUser> proffessoUserOpt = proffessoUserRepo.findByEmail(user.getEmail());
            proffessoUserOpt.ifPresent(proffessoUser -> duplicateProffessoUser(proffessoUser));
        });
    }
}
