package co.edu.usbcali.ecommerceusb.dto;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class UpdateProductCategoryRequest {
    private Integer productId;
    private Integer categoryId;
}
