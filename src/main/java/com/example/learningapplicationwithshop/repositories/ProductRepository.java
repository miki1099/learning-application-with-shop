package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
