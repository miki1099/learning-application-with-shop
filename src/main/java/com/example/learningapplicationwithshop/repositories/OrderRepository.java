package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findAllByCreateOrderDateBetween(Pageable pageable, LocalDate from, LocalDate to);

    Page<Order> findAllByUserId(Pageable pageable, int userId);

    Page<Order> findAllByIsRealisedFalse(Pageable pageable);

}
