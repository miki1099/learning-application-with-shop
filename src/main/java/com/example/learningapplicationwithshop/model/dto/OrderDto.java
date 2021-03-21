package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.Product;
import com.example.learningapplicationwithshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Access;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int id;

    private boolean isRealised;

    private User user;

    private Set<ProductDto> products = new HashSet<>();

}
