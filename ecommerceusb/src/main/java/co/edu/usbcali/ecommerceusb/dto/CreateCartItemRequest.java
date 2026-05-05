// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreateCartItemRequest {
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
}
