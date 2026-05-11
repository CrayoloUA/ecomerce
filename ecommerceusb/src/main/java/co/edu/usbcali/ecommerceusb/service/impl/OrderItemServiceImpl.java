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
import java.util.Objects;

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
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("OrderItem no encontrado con el id: %d", id)));
        return OrderItemMapper.modelToOrderItemResponse(item);
    }

    @Override
    public OrderItemResponse createOrderItem(CreateOrderItemRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createOrderItemRequest no puede ser nulo.");
        if (Objects.isNull(request.getOrderId()) || request.getOrderId() <= 0)
            throw new Exception("El campo orderId debe ser mayor a 0.");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0.");
        if (Objects.isNull(request.getQuantity()) || request.getQuantity() <= 0)
            throw new Exception("El campo quantity debe ser mayor a 0.");
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return OrderItemMapper.modelToOrderItemResponse(orderItemRepository.save(OrderItemMapper.createOrderItemRequestToOrderItem(request, order, product)));
    }

    @Override
    public OrderItemResponse updateOrderItem(Integer id, UpdateOrderItemRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateOrderItemRequest no puede ser nulo.");
        OrderItem item = orderItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("OrderItem no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getQuantity()) && request.getQuantity() > 0)
            item.setQuantity(request.getQuantity());
        if (!Objects.isNull(request.getUnitPriceSnapshot()))
            item.setUnitPriceSnapshot(request.getUnitPriceSnapshot());
        if (!Objects.isNull(request.getLineTotal()))
            item.setLineTotal(request.getLineTotal());
        return OrderItemMapper.modelToOrderItemResponse(orderItemRepository.save(item));
    }
}
