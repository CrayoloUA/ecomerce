package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderRequest;
import co.edu.usbcali.ecommerceusb.mapper.OrderMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return OrderMapper.modelToOrderResponse(orders);
    }

    @Override
    public OrderResponse getOrderById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Order no encontrada con el id: %d", id)));
        return OrderMapper.modelToOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createOrderRequest no puede ser nulo.");
        if (Objects.isNull(request.getUserId()) || request.getUserId() <= 0)
            throw new Exception("El campo userId debe ser mayor a 0.");
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo.");
        if (Objects.isNull(request.getTotalAmount())) throw new Exception("El campo totalAmount no puede ser nulo.");
        if (Objects.isNull(request.getCurrency()) || request.getCurrency().isBlank())
            throw new Exception("El campo currency no puede ser nulo.");
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + request.getUserId()));
        return OrderMapper.modelToOrderResponse(orderRepository.save(OrderMapper.createOrderRequestToOrder(request, user)));
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateOrderRequest no puede ser nulo.");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Order no encontrada con el id: %d", id)));
        if (!Objects.isNull(request.getStatus()) && !request.getStatus().isBlank())
            order.setStatus(Order.OrderStatus.valueOf(request.getStatus()));
        if (!Objects.isNull(request.getTotalAmount()))
            order.setTotalAmount(request.getTotalAmount());
        if (!Objects.isNull(request.getCurrency()) && !request.getCurrency().isBlank())
            order.setCurrency(request.getCurrency());
        return OrderMapper.modelToOrderResponse(orderRepository.save(order));
    }
}
