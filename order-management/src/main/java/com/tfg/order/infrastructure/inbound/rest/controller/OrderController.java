package com.tfg.order.infrastructure.inbound.rest.controller;

import com.tfg.order.application.OrderService;
import com.tfg.order.infrastructure.inbound.rest.dto.request.CreateOrderRequest;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request,
                                                     @RequestHeader("username") String username) {
        OrderResponse response = orderService.createOrder(request, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersForUser(@RequestHeader("username") String username) {
        List<OrderResponse> orders = orderService.getOrdersForUser(username);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id,
                                                      @RequestHeader("username") String username) {
        return orderService.getOrderById(username, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
