package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int id;

    private boolean isRealised;

    private UserWithoutOrdersDto user;

    private LocalDate createOrderDate;

    private BigDecimal price;

    private List<ProductDto> products = new ArrayList<>();

}
