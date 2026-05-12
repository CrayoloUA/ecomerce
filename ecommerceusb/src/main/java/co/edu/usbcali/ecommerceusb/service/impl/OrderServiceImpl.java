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
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order no encontrada con el id: " + id));
        return OrderMapper.modelToOrderResponse(order);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + request.getUserId()));
        return OrderMapper.modelToOrderResponse(orderRepository.save(OrderMapper.createOrderRequestToOrder(request, user)));
    }

    @Override
    public OrderResponse updateOrder(Integer id, UpdateOrderRequest request) throws Exception {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new Exception("Order no encontrada con el id: " + id));
        order.setStatus(Order.OrderStatus.valueOf(request.getStatus()));
        order.setTotalAmount(request.getTotalAmount());
        order.setCurrency(request.getCurrency());
        return OrderMapper.modelToOrderResponse(orderRepository.save(order));
    }
}
