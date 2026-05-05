// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class InventoryMovementResponse {
    private Integer id;
    private Integer productId;
    private Integer orderId;
    private String type;
    private Integer qty;
    private OffsetDateTime createdAt;
}
