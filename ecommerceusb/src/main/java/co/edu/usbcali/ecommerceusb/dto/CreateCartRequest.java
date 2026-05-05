// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreateCartRequest {
    private Integer userId;
    private String status;
}
