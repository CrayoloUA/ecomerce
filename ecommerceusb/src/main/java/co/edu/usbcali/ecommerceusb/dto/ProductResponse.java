// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
