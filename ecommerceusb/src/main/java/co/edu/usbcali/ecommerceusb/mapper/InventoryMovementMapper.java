// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryMovementRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryMovementResponse;
import co.edu.usbcali.ecommerceusb.model.InventoryMovement;
import co.edu.usbcali.ecommerceusb.model.Order;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryMovementMapper {

    public static InventoryMovementResponse modelToInventoryMovementResponse(InventoryMovement movement) {
        return InventoryMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProduct() != null ? movement.getProduct().getId() : null)
                .orderId(movement.getOrder() != null ? movement.getOrder().getId() : null)
                .type(movement.getType() != null ? movement.getType().name() : null)
                .qty(movement.getQty())
                .createdAt(movement.getCreatedAt())
                .build();
    }

    public static List<InventoryMovementResponse> modelToInventoryMovementResponse(List<InventoryMovement> movements) {
        return movements.stream()
                .map(InventoryMovementMapper::modelToInventoryMovementResponse)
                .collect(Collectors.toList());
    }

    public static InventoryMovement createInventoryMovementRequestToInventoryMovement(
            CreateInventoryMovementRequest request, Product product, Order order) {
        return InventoryMovement.builder()
                .product(product)
                .order(order)
                .type(InventoryMovement.MovementType.valueOf(request.getType()))
                .qty(request.getQty())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
