package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateInventoryRequest {
    private Integer stock;
}
