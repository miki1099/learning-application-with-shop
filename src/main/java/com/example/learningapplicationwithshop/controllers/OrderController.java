package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.OrderCreateDto;
import com.example.learningapplicationwithshop.model.dto.OrderDto;
import com.example.learningapplicationwithshop.services.OrderService;
import com.example.learningapplicationwithshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @RequestMapping(value = "/admin/order/getAll", method = RequestMethod.GET)
    public List<OrderDto> getAllOrdersPaged(@RequestParam int page, @RequestParam int size) {
        return orderService.getAllOrdersPaged(page, size);
    }

    @RequestMapping(value = "/admin/order/getAllCount", method = RequestMethod.GET)
    public int getAllOrdersPagedCount(@RequestParam int size) {
        return orderService.getAllOrdersPageCount(size);
    }

    @RequestMapping(value = "/admin/order/changeRealisation/{id}", method = RequestMethod.PUT)
    public OrderDto changeOrderRealisation(@PathVariable int id, @RequestParam boolean isRealised) {
        return orderService.changeOrdersRealisation(id, isRealised);
    }

    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public OrderDto createOrder(@RequestBody OrderCreateDto orderCreateDto, Principal principal) {
        return orderService.createOrder(getPrincipalUserId(principal), orderCreateDto);
    }

    @RequestMapping(value = "/admin/order/findAllCreatedBetween", method = RequestMethod.GET)
    public List<OrderDto> findAllCreatedBetweenPage(@RequestParam int page,
                                                        @RequestParam int size,
                                                    @RequestParam String from,
                                                    @RequestParam String to) {
        return orderService.findAllCreatedBetween(page, size, LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping(value = "/admin/order/findAllCreatedBetweenCount", method = RequestMethod.GET)
    public int findAllCreatedBetweenPage(@RequestParam int size,
                                            @RequestParam String from,
                                            @RequestParam String to) {
        return orderService.findAllCreatedBetweenPagesCount(size, LocalDate.parse(from), LocalDate.parse(to));
    }

    @RequestMapping(value = "/admin/order/findAllByUserId", method = RequestMethod.GET)
    public List<OrderDto> findAllByUserId(@RequestParam int page,
                                                    @RequestParam int size,
                                                    @RequestParam int userId) {
        return orderService.findAllByUserId(page, size, userId);
    }

    @RequestMapping(value = "/admin/order/findAllByUserIdCount", method = RequestMethod.GET)
    public int findAllByUserIdCount(@RequestParam int size,
                                          @RequestParam int userId) {
        return orderService.findAllByUserIdPagesCount(size, userId);
    }

    @RequestMapping(value = "/admin/order/findAllInProgress", method = RequestMethod.GET)
    public List<OrderDto> findAllByUserId(@RequestParam int page,
                                          @RequestParam int size) {
        return orderService.findAllOrdersInProgress(page, size);
    }

    @RequestMapping(value = "/admin/order/findAllInProgressCount", method = RequestMethod.GET)
    public int findAllByUserId(@RequestParam int size) {
        return orderService.findAllOrdersInProgressPagesCount(size);
    }

    private int getPrincipalUserId(Principal principal) {
        return userService.findByLogin(principal.getName()).getId();
    }
}
