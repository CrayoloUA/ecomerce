// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductCategoryMapper {

    public static ProductCategoryResponse modelToProductCategoryResponse(ProductCategory pc) {
        return ProductCategoryResponse.builder()
                .id(pc.getId())
                .productId(pc.getProduct() != null ? pc.getProduct().getId() : null)
                .categoryId(pc.getCategory() != null ? pc.getCategory().getId() : null)
                .build();
    }

    public static List<ProductCategoryResponse> modelToProductCategoryResponse(List<ProductCategory> pcs) {
        return pcs.stream()
                .map(ProductCategoryMapper::modelToProductCategoryResponse)
                .collect(Collectors.toList());
    }

    public static ProductCategory createProductCategoryRequestToProductCategory(
            CreateProductCategoryRequest request, Product product, Category category) {
        return ProductCategory.builder()
                .product(product)
                .category(category)
                .build();
    }
}
