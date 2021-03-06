package com.dev.football.controller;

import com.dev.football.model.Order;
import com.dev.football.model.ShoppingCart;
import com.dev.football.model.User;
import com.dev.football.model.dto.OrderResponseDto;
import com.dev.football.service.OrderService;
import com.dev.football.service.ShoppingCartService;
import com.dev.football.service.UserService;
import com.dev.football.service.impl.mapper.OrderMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(UserService userService,
                           ShoppingCartService shoppingCartService,
                           OrderService orderService,
                           OrderMapper orderMapper) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping("/complete")
    public void completeOrder(Authentication authentication) {
        String email = authentication.getName();
        ShoppingCart shoppingCart = shoppingCartService
                .getByUser(userService.findByEmail(email)
                        .get());
        orderService.completeOrder(shoppingCart);
    }

    @GetMapping
    public List<OrderResponseDto> getOrdersHistory(Authentication authentication) {
        String email = authentication.getName();
        User user = userService
                .findByEmail(email).get();
        List<Order> ordersHistory = orderService.getOrdersHistory(user);
        return ordersHistory.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
