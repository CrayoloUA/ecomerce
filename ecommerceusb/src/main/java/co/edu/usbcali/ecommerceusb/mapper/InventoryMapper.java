// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.model.Inventory;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryMapper {

    public static InventoryResponse modelToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProduct() != null ? inventory.getProduct().getId() : null)
                .stock(inventory.getStock())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }

    public static List<InventoryResponse> modelToInventoryResponse(List<Inventory> inventories) {
        return inventories.stream()
                .map(InventoryMapper::modelToInventoryResponse)
                .collect(Collectors.toList());
    }

    public static Inventory createInventoryRequestToInventory(CreateInventoryRequest request, Product product) {
        return Inventory.builder()
                .product(product)
                .stock(request.getStock())
                .updatedAt(OffsetDateTime.now())
                .build();
    }
}
