// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreateOrderRequest {
    private Integer userId;
    private String status;
    private BigDecimal totalAmount;
    private String currency;
}
