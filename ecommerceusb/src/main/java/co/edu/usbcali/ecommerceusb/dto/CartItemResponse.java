// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CartItemResponse {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
