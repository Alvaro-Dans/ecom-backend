package com.tfg.order.application;

import com.tfg.order.application.mapper.OrderMapper;
import com.tfg.order.infrastructure.outbound.persistence.entity.Order;
import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethod;
import com.tfg.order.infrastructure.outbound.persistence.entity.OrderItem;
import com.tfg.order.infrastructure.outbound.persistence.entity.ProductItemOrdered;
import com.tfg.order.infrastructure.inbound.rest.dto.request.CreateOrderRequest;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderResponse;
import com.tfg.order.infrastructure.outbound.persistence.repository.OrderRepository;
import com.tfg.order.infrastructure.outbound.persistence.repository.DeliveryMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        DeliveryMethodRepository deliveryMethodRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, String buyerEmail) {
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(request.getDeliveryMethodId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery method not found"));

        Order order = new Order();
        order.setBuyerEmail(buyerEmail);
        order.setShippingAddress(request.getShippingAddress());
        order.setDeliveryMethod(deliveryMethod);
        order.setPaymentSummary(request.getPaymentSummary());
        order.setDiscount(request.getDiscount() != null ? request.getDiscount() : java.math.BigDecimal.ZERO);
        order.setSubtotal(request.getOrderItems().stream()
                .map(i -> i.getPrice().multiply(new java.math.BigDecimal(i.getQuantity())))
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        order.setPaymentIntentId(request.getPaymentSummary().getBrand()); // Placeholder, adapt as needed

        // Map items
        List<OrderItem> items = request.getOrderItems().stream().map(iRequest -> {
            OrderItem item = new OrderItem();
            ProductItemOrdered itemOrdered = new ProductItemOrdered();
            itemOrdered.setProductId(iRequest.getProductId());
            itemOrdered.setProductName(iRequest.getProductName());
            itemOrdered.setPictureUrl(iRequest.getPictureUrl());
            item.setItemOrdered(itemOrdered);
            item.setPrice(iRequest.getPrice());
            item.setQuantity(iRequest.getQuantity());
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
