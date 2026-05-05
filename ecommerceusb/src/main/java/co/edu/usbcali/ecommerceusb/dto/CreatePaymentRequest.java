// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreatePaymentRequest {
    private Integer orderId;
    private String status;
    private String providerRef;
    private String idempotencyKey;
}
