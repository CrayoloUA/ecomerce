package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private Integer parentId;
}
