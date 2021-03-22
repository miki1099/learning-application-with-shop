package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
