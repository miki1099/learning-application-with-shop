package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;

    private String name;

    private String description;

    private String price;

    private Set<OrderDto> orders = new HashSet<>();

}
