package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.model.Category;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.model.ProductCategory;
import co.edu.usbcali.ecommerceusb.repository.CategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductCategoryRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> getProductCategories() {
        List<ProductCategory> pcs = productCategoryRepository.findAll();
        if (pcs.isEmpty()) return List.of();
        return pcs.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("ProductCategory no encontrado con id: " + id));
        return toResponse(pc);
    }

    @Override
    public ProductCategoryResponse createProductCategory(CreateProductCategoryRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0");
        if (Objects.isNull(request.getCategoryId()) || request.getCategoryId() <= 0)
            throw new Exception("El campo categoryId debe ser mayor a 0");

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Product no encontrado con id: " + request.getProductId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new Exception("Category no encontrada con id: " + request.getCategoryId()));

        ProductCategory pc = ProductCategory.builder()
                .product(product)
                .category(category)
                .build();

        return toResponse(productCategoryRepository.save(pc));
    }

    private ProductCategoryResponse toResponse(ProductCategory pc) {
        return ProductCategoryResponse.builder()
                .id(pc.getId())
                .productId(pc.getProduct().getId())
                .categoryId(pc.getCategory().getId())
                .build();
    }
}
