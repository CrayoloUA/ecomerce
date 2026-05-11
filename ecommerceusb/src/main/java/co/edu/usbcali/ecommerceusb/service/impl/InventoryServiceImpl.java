package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateInventoryRequest;
import co.edu.usbcali.ecommerceusb.mapper.InventoryMapper;
import co.edu.usbcali.ecommerceusb.model.Inventory;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.InventoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<InventoryResponse> getInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        if (inventories.isEmpty()) return List.of();
        return InventoryMapper.modelToInventoryResponse(inventories);
    }

    @Override
    public InventoryResponse getInventoryById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Inventory no encontrado con el id: %d", id)));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createInventoryRequest no puede ser nulo.");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0.");
        if (Objects.isNull(request.getStock()) || request.getStock() < 0)
            throw new Exception("El campo stock no puede ser negativo.");
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(InventoryMapper.createInventoryRequestToInventory(request, product)));
    }

    @Override
    public InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateInventoryRequest no puede ser nulo.");
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Inventory no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getStock()) && request.getStock() >= 0)
            inventory.setStock(request.getStock());
        inventory.setUpdatedAt(OffsetDateTime.now());
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }
}
