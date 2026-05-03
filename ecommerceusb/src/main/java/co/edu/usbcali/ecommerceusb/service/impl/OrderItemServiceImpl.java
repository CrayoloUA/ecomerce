package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.OrderItemRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderItemResponse> getOrderItems() {
        List<OrderItem> items = orderItemRepository.findAll();
        if (items.isEmpty()) return List.of();
        return items.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public OrderItemResponse getOrderItemById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception("OrderItem no encontrado con id: " + id));
        return toResponse(item);
    }

    @Override
    public OrderItemResponse createOrderItem(CreateOrderItemRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getOrderId()) || request.getOrderId() <= 0)
            throw new Exception("El campo orderId debe ser mayor a 0");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0");
        if (Objects.isNull(request.getQuantity()) || request.getQuantity() <= 0)
            throw new Exception("El campo quantity debe ser mayor a 0");

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Product no encontrado con id: " + request.getProductId()));

        OrderItem item = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPriceSnapshot(request.getUnitPriceSnapshot())
                .lineTotal(request.getLineTotal())
                .createdAt(OffsetDateTime.now())
                .build();

        return toResponse(orderItemRepository.save(item));
    }

    private OrderItemResponse toResponse(OrderItem i) {
        return OrderItemResponse.builder()
                .id(i.getId())
                .orderId(i.getOrder().getId())
                .productId(i.getProduct().getId())
                .quantity(i.getQuantity())
                .unitPriceSnapshot(i.getUnitPriceSnapshot())
                .lineTotal(i.getLineTotal())
                .createdAt(i.getCreatedAt())
                .build();
    }
}
