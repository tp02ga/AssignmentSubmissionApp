package com.coderscampus.proffesso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderscampus.proffesso.domain.ProffessoUser;

public interface ProffessoUserRepo extends JpaRepository<ProffessoUser, Long> {
    Optional<ProffessoUser> findByEmail(String username);
}
