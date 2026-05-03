package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryMovementRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<InventoryMovementResponse> getInventoryMovements() {
        List<InventoryMovement> movements = inventoryMovementRepository.findAll();
        if (movements.isEmpty()) return List.of();
        return movements.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public InventoryMovementResponse getInventoryMovementById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new Exception("InventoryMovement no encontrado con id: " + id));
        return toResponse(movement);
    }

    @Override
    public InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0");
        if (Objects.isNull(request.getType()) || request.getType().isBlank())
            throw new Exception("El campo type no puede ser nulo");
        if (Objects.isNull(request.getQty()) || request.getQty() <= 0)
            throw new Exception("El campo qty debe ser mayor a 0");

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Product no encontrado con id: " + request.getProductId()));

        Order order = null;
        if (request.getOrderId() != null) {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        }

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .order(order)
                .type(InventoryMovement.MovementType.valueOf(request.getType()))
                .qty(request.getQty())
                .createdAt(OffsetDateTime.now())
                .build();

        return toResponse(inventoryMovementRepository.save(movement));
    }

    private InventoryMovementResponse toResponse(InventoryMovement m) {
        return InventoryMovementResponse.builder()
                .id(m.getId())
                .productId(m.getProduct().getId())
                .orderId(m.getOrder() != null ? m.getOrder().getId() : null)
                .type(m.getType().name())
                .qty(m.getQty())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
