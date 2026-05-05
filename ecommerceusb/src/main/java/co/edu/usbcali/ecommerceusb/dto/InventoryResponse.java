// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class InventoryResponse {
    private Integer id;
    private Integer productId;
    private Integer stock;
    private OffsetDateTime updatedAt;
}
