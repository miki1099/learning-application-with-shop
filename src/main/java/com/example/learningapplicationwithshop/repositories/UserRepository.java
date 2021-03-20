package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAll(Pageable pageable);

}
