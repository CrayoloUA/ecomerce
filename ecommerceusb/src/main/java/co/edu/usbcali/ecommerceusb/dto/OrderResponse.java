// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
    private OffsetDateTime createdAt;
}
