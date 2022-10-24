package com.coderscampus.proffesso.repository;

import com.coderscampus.proffesso.domain.ProffessoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface ProffessoUserRepo extends JpaRepository<ProffessoUser, Long> {
    @Query("select u from ProffessoUser u "
            + "left join fetch u.authorities "
            + "where u.email = :username ")
    Optional<ProffessoUser> findByEmail(String username);

    @Query(value = "CALL find_bootcamp_students();", nativeQuery = true)
    List<Tuple> findBootcampStudents();

    @Query("select u from ProffessoUser u " +
            "join u.orders o " +
            "join o.offers off " +
            "where off.id = 225 " +
            "and (o.suspendOn is not null and o.suspendOn < now())")
    List<ProffessoUser> findDroppedBootcampStudents();
}
