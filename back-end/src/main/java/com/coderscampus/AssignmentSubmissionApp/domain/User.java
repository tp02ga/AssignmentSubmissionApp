package com.coderscampus.AssignmentSubmissionApp.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

import com.coderscampus.proffesso.domain.ProffessoUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    private static final long serialVersionUID = 1840361243951715062L;
    @Id
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Authorities> authorities = new HashSet<>();
    
    public User () {}
    public User (ProffessoUser proffessoUser, Optional<User> appUserOpt) {
        proffessoUser.getAuthorities().stream()
        .forEach(auth -> {
            Authorities newAuth = new Authorities();
            newAuth.setAuthority(auth.getAuthority());
            newAuth.setUser(this);
            this.getAuthorities().add(newAuth);
        });
        appUserOpt.ifPresent(appUser -> {
            appUser.getAuthorities()
                .stream()
                .forEach(auth -> {
                    Authorities newAuth = new Authorities();
                    newAuth.setAuthority(auth.getAuthority());
                    newAuth.setUser(this);
                    this.getAuthorities().add(newAuth);
                });
        });
        this.username = proffessoUser.getEmail();
        this.id = proffessoUser.getId();
        this.name = proffessoUser.getName();
        this.password = proffessoUser.getPassword();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String toString() {
        return "User [username=" + username + "]";
    }


}
