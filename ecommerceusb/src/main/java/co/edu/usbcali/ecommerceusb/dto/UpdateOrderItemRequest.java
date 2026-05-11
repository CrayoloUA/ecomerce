package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateOrderItemRequest {
    private Integer quantity;
    private BigDecimal unitPriceSnapshot;
    private BigDecimal lineTotal;
}
