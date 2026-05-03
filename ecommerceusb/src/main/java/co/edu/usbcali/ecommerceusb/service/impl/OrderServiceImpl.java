package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) return List.of();
        return orders.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse getOrderById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + id));
        return toResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getUserId()) || request.getUserId() <= 0)
            throw new Exception("El campo userId debe ser mayor a 0");
        if (Objects.isNull(request.getTotalAmount()) || request.getTotalAmount().doubleValue() < 0)
            throw new Exception("El campo totalAmount no puede ser negativo");
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isBlank())
            throw new Exception("El campo currency no puede ser nulo");
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo");

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("User no encontrado con id: " + request.getUserId()));

        Order order = Order.builder()
                .user(user)
                .status(Order.OrderStatus.valueOf(request.getStatus()))
                .totalAmount(request.getTotalAmount())
                .currency(request.getCurrency())
                .createdAt(OffsetDateTime.now())
                .build();

        return toResponse(orderRepository.save(order));
    }

    private OrderResponse toResponse(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .userId(o.getUser().getId())
                .status(o.getStatus().name())
                .totalAmount(o.getTotalAmount())
                .currency(o.getCurrency())
                .createdAt(o.getCreatedAt())
                .paidAt(o.getPaidAt())
                .cancelledAt(o.getCancelledAt())
                .build();
    }
}
