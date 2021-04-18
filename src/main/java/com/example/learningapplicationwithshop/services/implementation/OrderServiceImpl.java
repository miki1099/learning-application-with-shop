package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.NotFoundException;
import com.example.learningapplicationwithshop.exceptions.ProductOutOfStockException;
import com.example.learningapplicationwithshop.exceptions.UserDoesNotHaveRequiredField;
import com.example.learningapplicationwithshop.model.Order;
import com.example.learningapplicationwithshop.model.Product;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.OrderCreateDto;
import com.example.learningapplicationwithshop.model.dto.OrderDto;
import com.example.learningapplicationwithshop.repositories.OrderRepository;
import com.example.learningapplicationwithshop.repositories.ProductRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDto> getAllOrdersPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getAllOrdersPageCount(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return orderRepository.findAll(pageable).getTotalPages();
    }

    @Override
    @Transactional
    public OrderDto createOrder(int userId, OrderCreateDto createdOrder) {
        List<Integer> productsId = createdOrder.getProductsIds();
        if(productsId == null || productsId.size() == 0) throw new NotFoundException("No products were found!");

        List<Product> products = productRepository.findAllById(productsId);
        User orderUser = getUserSafe(userId);
        userChecker(orderUser);
        BigDecimal orderPrice = BigDecimal.ZERO;
        for (Product product : products) {
            int available = product.getNumberAvailable();
            if(available <= 0) {
                throw new ProductOutOfStockException("Product " + product.getName() + " is out of" +
                        "stock!");
            }
            orderPrice = orderPrice.add(product.getPrice());
            product.setNumberAvailable(available-1);
        }

        Order newOrder = new Order();
        newOrder.setCreateOrderDate(LocalDate.now());
        newOrder.setProducts(products);
        newOrder.setPrice(orderPrice);
        newOrder.setUser(orderUser);
        newOrder.setRealised(false);
        return modelMapper.map(orderRepository.save(newOrder), OrderDto.class);
    }

    @Override
    @Transactional
    public OrderDto changeOrdersRealisation(int id, boolean isRealised) {
        Order orderToChange = getOneSafe(id);
        orderToChange.setRealised(isRealised);
        return modelMapper.map(orderToChange, OrderDto.class);
    }

    @Override
    public List<OrderDto> findAllCreatedBetween(int page, int size, LocalDate from, LocalDate to) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllByCreateOrderDateBetween(pageable, from, to)
                .getContent()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int findAllCreatedBetweenPagesCount(int size, LocalDate from, LocalDate to) {
        Pageable pageable = PageRequest.of(0, size);
        return orderRepository.findAllByCreateOrderDateBetween(pageable, from, to).getTotalPages();
    }

    @Override
    public List<OrderDto> findAllByUserId(int page, int size, int userId) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllByUserId(pageable, userId)
                .getContent()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int findAllByUserIdPagesCount(int size, int userId) {
        Pageable pageable = PageRequest.of(0, size);
        return orderRepository.findAllByUserId(pageable, userId).getTotalPages();
    }

    @Override
    public List<OrderDto> findAllOrdersInProgress(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAllByIsRealisedFalse(pageable)
                .getContent()
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int findAllOrdersInProgressPagesCount(int size) {
        Pageable pageable = PageRequest.of(0, size);
        return orderRepository.findAllByIsRealisedFalse(pageable).getTotalPages();
    }

    private Order getOneSafe(int id) {
        if(orderRepository.existsById(id)) {
            return orderRepository.getOne(id);
        } else {
            throw new NotFoundException("Order with this id: " + id + " was not found!");
        }
    }

    private User getUserSafe(int userId) {
        if(userRepository.existsById(userId)) {
            return userRepository.getOne(userId);
        } else {
            throw new NotFoundException("User with this id: " + userId + " was not found!");
        }
    }

    private void userChecker(User user) {
        if(user.getAddress() == null || user.getName() == null ||
            user.getLastName() == null || user.getPhone() == null)
            throw new UserDoesNotHaveRequiredField("User doesn't have required info to place order!");
    }

}
