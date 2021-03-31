package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Product;
import com.example.learningapplicationwithshop.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Product> findAllByNumberAvailableIsGreaterThan(int minNumberAvailable,Pageable pageable);

    Page<Product> findAllByNumberAvailableIsGreaterThanOrderByPriceAsc(int minNumberAvailable, Pageable pageable);

    Page<Product> findAllByNumberAvailableIsGreaterThanOrderByPriceDesc(int minNumberAvailable, Pageable pageable);
}
