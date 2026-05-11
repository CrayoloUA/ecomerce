package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateOrderRequest {
    private String status;
    private BigDecimal totalAmount;
    private String currency;
}
