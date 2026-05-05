// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse modelToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser() != null ? order.getUser().getId() : null)
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .total(order.getTotal())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public static List<OrderResponse> modelToOrderResponse(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::modelToOrderResponse)
                .collect(Collectors.toList());
    }

    public static Order createOrderRequestToOrder(CreateOrderRequest request, User user) {
        return Order.builder()
                .user(user)
                .status(Order.OrderStatus.valueOf(request.getStatus()))
                .total(request.getTotal())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
