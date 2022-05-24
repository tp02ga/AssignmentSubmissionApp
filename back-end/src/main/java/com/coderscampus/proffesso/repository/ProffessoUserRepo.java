package com.coderscampus.proffesso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.coderscampus.proffesso.domain.ProffessoUser;

public interface ProffessoUserRepo extends JpaRepository<ProffessoUser, Long> {
    @Query("select u from ProffessoUser u "
            + "left join fetch u.authorities "
            + "where u.email = :username ")
    Optional<ProffessoUser> findByEmail(String username);
}
