package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateCartItemRequest {
    private Integer quantity;
}
