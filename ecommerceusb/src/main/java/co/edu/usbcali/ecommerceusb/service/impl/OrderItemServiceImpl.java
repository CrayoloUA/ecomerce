package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.dto.OrderItemResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateOrderItemRequest;
import co.edu.usbcali.ecommerceusb.mapper.OrderItemMapper;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.OrderItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.OrderItemRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return OrderItemMapper.modelToOrderItemResponse(items);
    }

    @Override
    public OrderItemResponse getOrderItemById(Integer id) throws Exception {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception("OrderItem no encontrado con el id: " + id));
        return OrderItemMapper.modelToOrderItemResponse(item);
    }

    @Override
    public OrderItemResponse createOrderItem(CreateOrderItemRequest request) throws Exception {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return OrderItemMapper.modelToOrderItemResponse(orderItemRepository.save(OrderItemMapper.createOrderItemRequestToOrderItem(request, order, product)));
    }

    @Override
    public OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest request) throws Exception {
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception("OrderItem no encontrado con el id: " + id));
        item.setQuantity(request.getQuantity());
        item.setUnitPriceSnapshot(request.getUnitPriceSnapshot());
        item.setLineTotal(request.getLineTotal());
        return OrderItemMapper.modelToOrderItemResponse(orderItemRepository.save(item));
    }
}
