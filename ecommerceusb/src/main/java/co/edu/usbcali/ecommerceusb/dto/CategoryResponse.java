// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CategoryResponse {
    private Integer id;
    private String name;
    private Integer parentId;
    private OffsetDateTime createdAt;
}
