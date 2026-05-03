// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Integer id;
    private Integer orderId;
    private String status;
    private String providerRef;
    private String idempotencyKey;
    private OffsetDateTime createdAt;
}
