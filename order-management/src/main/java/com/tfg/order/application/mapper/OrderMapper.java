package com.tfg.order.application.mapper;

import com.tfg.order.infrastructure.outbound.persistence.entity.Order;
import com.tfg.order.infrastructure.outbound.persistence.entity.OrderItem;
import com.tfg.order.infrastructure.outbound.persistence.entity.DeliveryMethod;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderItemResponse;
import com.tfg.order.infrastructure.inbound.rest.dto.response.OrderResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "deliveryMethod.shortName", target = "deliveryMethod")
    @Mapping(source = "deliveryMethod.price", target = "shippingPrice")
    @Mapping(source = "orderItems", target = "orderItems")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "paymentSummary", target = "paymentSummary")
    public OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orders);

    default List<OrderItemResponse> mapOrderItems(List<OrderItem> items) {
        return items.stream().map(this::mapOrderItem).collect(Collectors.toList());
    }

    @Mapping(source = "itemOrdered.productId", target = "productId")
    @Mapping(source = "itemOrdered.productName", target = "productName")
    @Mapping(source = "itemOrdered.pictureUrl", target = "pictureUrl")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    public OrderItemResponse mapOrderItem(OrderItem item);
}
