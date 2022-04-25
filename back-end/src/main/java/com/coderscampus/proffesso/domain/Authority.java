package com.coderscampus.proffesso.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = -6520888182797362903L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToOne(optional = false)
    @JsonIgnore
    private ProffessoUser user;
    
    public Authority () {}
    
    public Authority (String authority) {
        this.authority = authority;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public ProffessoUser getUser() {
        return user;
    }
    public void setUser(ProffessoUser user) {
        this.user = user;
    }
    
}
