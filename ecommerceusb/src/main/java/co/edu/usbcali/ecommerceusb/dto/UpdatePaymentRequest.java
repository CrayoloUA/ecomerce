package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdatePaymentRequest {
    private String status;
    private String providerRef;
}
