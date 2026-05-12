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
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory no encontrado con el id: " + id));
        return InventoryMapper.modelToInventoryResponse(inventory);
    }

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) throws Exception {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(InventoryMapper.createInventoryRequestToInventory(request, product)));
    }

    @Override
    public InventoryResponse updateInventory(Integer id, UpdateInventoryRequest request) throws Exception {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Inventory no encontrado con el id: " + id));
        inventory.setStock(request.getStock());
        inventory.setUpdatedAt(OffsetDateTime.now());
        return InventoryMapper.modelToInventoryResponse(inventoryRepository.save(inventory));
    }
}
