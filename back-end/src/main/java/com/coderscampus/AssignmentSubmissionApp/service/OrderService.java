package com.coderscampus.AssignmentSubmissionApp.service;

import com.coderscampus.proffesso.domain.Offer;
import com.coderscampus.proffesso.domain.Order;
import com.coderscampus.proffesso.domain.ProffessoUser;
import com.coderscampus.proffesso.repository.OrderRepository;
import com.coderscampus.proffesso.repository.ProffessoUserRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    public static final List<Long> BOOTCAMP_OFFER_IDS = List.of(225L, 221L, 218L, 212L);
    public static final Long JAVA_FOUNDATIONS_OFFER_ID = 227L;

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
