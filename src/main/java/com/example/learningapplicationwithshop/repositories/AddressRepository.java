package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
