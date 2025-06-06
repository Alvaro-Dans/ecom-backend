package com.tfg.order.application;

import com.tfg.order.application.dto.CartResponse;
import com.tfg.order.application.mapper.OrderMapper;
import com.tfg.order.infrastructure.outbound.persistence.entity.Order;
import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethods;
import com.tfg.order.infrastructure.outbound.persistence.entity.OrderItem;
import com.tfg.order.infrastructure.outbound.persistence.entity.ProductItemOrdered;
import com.tfg.order.infrastructure.inbound.rest.dto.request.CreateOrderRequest;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderResponse;
import com.tfg.order.infrastructure.outbound.persistence.repository.OrderRepository;
import com.tfg.order.infrastructure.outbound.persistence.repository.DeliveryMethodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Value("${cart.url}")
    private String CART_URL;

    private final OrderRepository orderRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final OrderMapper orderMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public OrderService(OrderRepository orderRepository,
                        DeliveryMethodRepository deliveryMethodRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, String buyerEmail, String jSession) {
        DeliveryMethods deliveryMethods = deliveryMethodRepository.findById(request.getDeliveryMethodId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery method not found"));

        Order order = new Order();
        order.setBuyerEmail(buyerEmail);
        order.setShippingAddress(request.getShippingAddress());
        order.setDeliveryMethods(deliveryMethods);
        order.setPaymentSummary(request.getPaymentSummary());
        order.setDiscount(request.getDiscount() != null ? request.getDiscount() : java.math.BigDecimal.ZERO);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + jSession);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartResponse> cartResponse;
        try {
            cartResponse = restTemplate.exchange(
                    CART_URL + "?id=" + request.getCartId(),
                    HttpMethod.GET,
                    entity,
                    CartResponse.class
            );
        } catch (Exception ex) {
            return null;
        }

        order.setSubtotal(cartResponse.getBody().getItems().stream()
                .map(i -> i.price().multiply(new java.math.BigDecimal(i.quantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        order.setPaymentIntentId(cartResponse.getBody().getPaymentIntentId()); // Placeholder, adapt as needed

        // Map items
        List<OrderItem> items = cartResponse.getBody().getItems().stream().map(iRequest -> {
            OrderItem item = new OrderItem();
            ProductItemOrdered itemOrdered = new ProductItemOrdered();
            itemOrdered.setProductId(iRequest.productId());
            itemOrdered.setProductName(iRequest.productName());
            itemOrdered.setPictureUrl(iRequest.pictureUrl());
            item.setItemOrdered(itemOrdered);
            item.setPrice(iRequest.price());
            item.setQuantity(iRequest.quantity());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setOrderItems(items);

        Order saved = orderRepository.save(order);
        return orderMapper.toOrderResponse(saved);
    }

    public List<OrderResponse> getOrdersForUser(String buyerEmail) {
        List<Order> orders = orderRepository.findByBuyerEmail(buyerEmail);
        return orderMapper.toOrderResponseList(orders);
    }

    public Optional<OrderResponse> getOrderById(String buyerEmail, Long id) {
        return orderRepository.findByIdAndBuyerEmail(id, buyerEmail)
                .map(orderMapper::toOrderResponse);
    }
}
