package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) return List.of();
        return categories.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category no encontrada con id: " + id));
        return toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getName()) || request.getName().isBlank())
            throw new Exception("El campo name no puede ser nulo");

        Category parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new Exception("Parent category no encontrada con id: " + request.getParentId()));
        }

        Category category = Category.builder()
                .name(request.getName())
                .parent(parent)
                .createdAt(OffsetDateTime.now())
                .build();

        return toResponse(categoryRepository.save(category));
    }

    private CategoryResponse toResponse(Category c) {
        return CategoryResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .parentId(c.getParent() != null ? c.getParent().getId() : null)
                .createdAt(c.getCreatedAt())
                .build();
    }
}
