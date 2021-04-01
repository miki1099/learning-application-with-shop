package com.example.learningapplicationwithshop.services;

import com.example.learningapplicationwithshop.model.dto.OrderCreateDto;
import com.example.learningapplicationwithshop.model.dto.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrdersPaged(int page, int size);

    int getAllOrdersPageCount(int size);

    OrderDto createOrder(OrderCreateDto createdOrder);

    OrderDto changeOrdersRealisation(int id, boolean isRealised);

    List<OrderDto> findAllCreatedBetween(int page, int size, LocalDate from, LocalDate to);

    int findAllCreatedBetweenPagesCount(int size, LocalDate from, LocalDate to);

    List<OrderDto> findAllByUserId(int page, int size, int userId);

    int findAllByUserIdPagesCount(int size, int userId);

    List<OrderDto> findAllOrdersInProgress(int page, int size);

    int findAllOrdersInProgressPagesCount(int size);
}
