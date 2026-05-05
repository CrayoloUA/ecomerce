// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreateInventoryMovementRequest {
    private Integer productId;
    private Integer orderId;
    private String type;
    private Integer qty;
}
