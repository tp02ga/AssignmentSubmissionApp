package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.proffesso.domain.Offer;
import com.coderscampus.proffesso.domain.Order;
import com.coderscampus.proffesso.domain.ProffessoUser;
import com.coderscampus.proffesso.repository.OrderRepository;
import com.coderscampus.proffesso.repository.ProffessoUserRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    public static final Long BOOTCAMP_OFFER_ID = 225L;
    public static final Long JAVA_FOUNDATIONS_OFFER_ID = 226L;

    private OrderRepository orderRepo;
    private ProffessoUserRepo proffessoUserRepo;

    public OrderService(OrderRepository orderRepo, ProffessoUserRepo proffessoUserRepo) {
        this.orderRepo = orderRepo;
        this.proffessoUserRepo = proffessoUserRepo;
    }

    public Set<Offer> findStudentOrdersByUserId(Long userId) {
        Optional<ProffessoUser> proffessoUserOpt = proffessoUserRepo.findById(userId);

        if (proffessoUserOpt.isPresent()) {
            Set<Order> orders = orderRepo.findByUser(proffessoUserOpt.get());

            Set<Offer> offers = orders.stream()
                    .map(order -> order.getOffers())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());

            return offers;
        }
        return null;
    }
}
