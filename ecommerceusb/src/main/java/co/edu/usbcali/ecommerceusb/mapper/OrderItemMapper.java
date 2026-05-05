// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {

    public static OrderItemResponse modelToOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)
                .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                .qty(orderItem.getQty())
                .unitPrice(orderItem.getUnitPrice())
                .build();
    }

    public static List<OrderItemResponse> modelToOrderItemResponse(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItemMapper::modelToOrderItemResponse)
                .collect(Collectors.toList());
    }

    public static OrderItem createOrderItemRequestToOrderItem(CreateOrderItemRequest request, Order order, Product product) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .qty(request.getQty())
                .unitPrice(request.getUnitPrice())
                .build();
    }
}
