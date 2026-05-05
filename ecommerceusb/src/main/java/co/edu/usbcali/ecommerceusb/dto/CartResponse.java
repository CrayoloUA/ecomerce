// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CartResponse {
    private Integer id;
    private Integer userId;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
