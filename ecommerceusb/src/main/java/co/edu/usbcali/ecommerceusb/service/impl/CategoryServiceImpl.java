// tarea
package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.mapper.CategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) return List.of();
        return CategoryMapper.modelToCategoryResponse(categories);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Categoría no encontrada con el id: %d", id)));
        return CategoryMapper.modelToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createCategoryRequest no puede ser nulo.");
        if (Objects.isNull(request.getName()) || request.getName().isBlank())
            throw new Exception("El campo name no puede ser nulo.");
        Category category = CategoryMapper.createCategoryRequestToCategory(request);
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new Exception("Categoría padre no encontrada con id: " + request.getParentId()));
            category.setParent(parent);
        }
        return CategoryMapper.modelToCategoryResponse(categoryRepository.save(category));
    }
}
