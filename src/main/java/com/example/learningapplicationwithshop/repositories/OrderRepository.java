package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
