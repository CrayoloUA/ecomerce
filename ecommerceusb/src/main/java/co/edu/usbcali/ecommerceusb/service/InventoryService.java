package co.edu.usbcali.ecommerceusb.service;

import co.edu.usbcali.ecommerceusb.dto.CreateInventoryRequest;
import co.edu.usbcali.ecommerceusb.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getInventories();
    InventoryResponse getInventoryById(Integer id) throws Exception;
    InventoryResponse createInventory(CreateInventoryRequest request) throws Exception;
}
