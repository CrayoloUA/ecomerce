// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
    private BigDecimal lineTotal;
    private OffsetDateTime createdAt;
}
