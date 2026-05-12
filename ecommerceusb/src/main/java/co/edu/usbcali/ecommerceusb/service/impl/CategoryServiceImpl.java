package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCategoryRequest;
import co.edu.usbcali.ecommerceusb.mapper.CategoryMapper;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Categoria no encontrada con el id: " + id));
        return CategoryMapper.modelToCategoryResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) throws Exception {
        Category category = CategoryMapper.createCategoryRequestToCategory(request);
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new Exception("Categoria padre no encontrada"));
            category.setParent(parent);
        }
        return CategoryMapper.modelToCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Categoria no encontrada con el id: " + id));
        category.setName(request.getName());
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new Exception("Categoria padre no encontrada"));
            category.setParent(parent);
        }
        return CategoryMapper.modelToCategoryResponse(categoryRepository.save(category));
    }
}
