// tarea
package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class CreateCategoryRequest {
    private String name;
    private Integer parentId;
}
