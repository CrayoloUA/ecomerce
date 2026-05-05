// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.model.Category;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryResponse modelToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .createdAt(category.getCreatedAt())
                .build();
    }

    public static List<CategoryResponse> modelToCategoryResponse(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::modelToCategoryResponse)
                .collect(Collectors.toList());
    }

    public static Category createCategoryRequestToCategory(CreateCategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
