package com.tfg.order.application.mapper;

import com.tfg.order.infrastructure.outbound.persistence.entity.*;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderItemResponse;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderResponse;
import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethods;
import com.tfg.order.infrastructure.outbound.persistence.entity.Order;
import com.tfg.order.infrastructure.outbound.persistence.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OrderMapper {

    /**
     * Convierte una entidad Order en su DTO OrderResponse.
     */
    public OrderResponse toOrderResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponse dto = new OrderResponse();

        // 1) ID
        dto.setId(order.getId());

        // 2) orderDate
        dto.setOrderDate(order.getOrderDate());

        // 3) buyerEmail
        dto.setBuyerEmail(order.getBuyerEmail());

        // 4) shippingAddress (convertimos a DTO)
        ShippingAddress sa = order.getShippingAddress();
        if (sa != null) {
            ShippingAddress saDto = new ShippingAddress();
            saDto.setLine1(sa.getLine1());
            saDto.setCity(sa.getCity());
            saDto.setState(sa.getState());
            saDto.setPostalCode(sa.getPostalCode());
            saDto.setCountry(sa.getCountry());
            dto.setShippingAddress(saDto);
        } else {
            dto.setShippingAddress(null);
        }

        // 5) deliveryMethods → deliveryMethod (shortName) y shippingPrice (price)
        DeliveryMethods dm = order.getDeliveryMethods();
        if (dm != null) {
            dto.setDeliveryMethod(dm.getShortName());
            dto.setShippingPrice(dm.getPrice());
        } else {
            dto.setDeliveryMethod(null);
            dto.setShippingPrice(null);
        }

        // 6) orderItems → lista de OrderItemResponse
        List<OrderItem> items = order.getOrderItems();
        if (items != null && !items.isEmpty()) {
            dto.setOrderItems(mapOrderItems(items));
        } else {
            dto.setOrderItems(Collections.emptyList());
        }

        // 7) subtotal
        dto.setSubtotal(order.getSubtotal());

        // 8) discount
        dto.setDiscount(order.getDiscount());

        // 9) paymentIntentId
        dto.setPaymentIntentId(order.getPaymentIntentId());

        // 10) status (como string)
        dto.setStatus(order.getStatus().toString());

        // 11) paymentSummary
        PaymentSummary ps = order.getPaymentSummary();
        dto.setPaymentSummary(ps);

        // 12) total (si quieres calcularlo aquí en lugar de en el DTO)
        java.math.BigDecimal ship = (dm != null ? dm.getPrice() : java.math.BigDecimal.ZERO);
        java.math.BigDecimal total = order.getSubtotal()
                .subtract(order.getDiscount() != null ? order.getDiscount() : java.math.BigDecimal.ZERO)
                .add(ship);
        dto.setTotal(total);

        return dto;
    }

    /**
     * Convierte lista de Order en lista de OrderResponse
     */
    public List<OrderResponse> toOrderResponseList(List<Order> orders) {
        if (orders == null) {
            return Collections.emptyList();
        }
        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    /**
     * Convierte lista de OrderItem en lista de OrderItemResponse
     */
    private List<OrderItemResponse> mapOrderItems(List<OrderItem> items) {
        return items.stream()
                .map(this::mapOrderItem)
                .collect(Collectors.toList());
    }

    /**
     * Convierte un OrderItem en OrderItemResponse
     */
    private OrderItemResponse mapOrderItem(OrderItem item) {
        if (item == null) {
            return null;
        }

        OrderItemResponse dto = new OrderItemResponse();

        ProductItemOrdered io = item.getItemOrdered();
        if (io != null) {
            dto.setProductId(io.getProductId());
            dto.setProductName(io.getProductName());
            dto.setPictureUrl(io.getPictureUrl());
        } else {
            dto.setProductId(null);
            dto.setProductName(null);
            dto.setPictureUrl(null);
        }

        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());

        return dto;
    }
}

