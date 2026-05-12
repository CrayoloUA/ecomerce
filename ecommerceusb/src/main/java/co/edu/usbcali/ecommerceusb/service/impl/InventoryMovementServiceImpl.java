package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMovementMapper;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryMovementRepository;
import co.edu.usbcali.ecommerceusb.repository.OrderRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return InventoryMovementMapper.modelToInventoryMovementResponse(movements);
    }

    @Override
    public InventoryMovementResponse getInventoryMovementById(Integer id) throws Exception {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new Exception("InventoryMovement no encontrado con el id: " + id));
        return InventoryMovementMapper.modelToInventoryMovementResponse(movement);
    }

    @Override
    public InventoryMovementResponse createInventoryMovement(CreateInventoryMovementRequest request) throws Exception {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        Order order = null;
        if (request.getOrderId() != null) {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new Exception("Order no encontrada con id: " + request.getOrderId()));
        }
        return InventoryMovementMapper.modelToInventoryMovementResponse(inventoryMovementRepository.save(InventoryMovementMapper.createInventoryMovementRequestToInventoryMovement(request, product, order)));
    }

    @Override
    public InventoryMovementResponse updateInventoryMovement(Integer id, UpdateInventoryMovementRequest request) throws Exception {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new Exception("InventoryMovement no encontrado con el id: " + id));
        movement.setType(InventoryMovement.MovementType.valueOf(request.getType()));
        movement.setQty(request.getQty());
        return InventoryMovementMapper.modelToInventoryMovementResponse(inventoryMovementRepository.save(movement));
    }
}
