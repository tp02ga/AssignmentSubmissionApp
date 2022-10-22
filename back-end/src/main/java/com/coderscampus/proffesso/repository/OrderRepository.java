package com.coderscampus.proffesso.repository;

import com.coderscampus.proffesso.domain.Order;
import com.coderscampus.proffesso.domain.ProffessoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    Page<Order> findByOffersIn(@Param(value = "offers") Set<Offer> offers, Pageable page);

    @Query("select o from Order o " +
            "join fetch o.offers " +
            "where o.user = :user")
    Set<Order> findByUser(@Param(value = "user") ProffessoUser user);

}