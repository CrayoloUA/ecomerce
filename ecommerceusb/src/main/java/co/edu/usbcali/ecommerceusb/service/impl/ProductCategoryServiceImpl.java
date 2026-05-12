package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductCategoryResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateProductCategoryRequest;
import co.edu.usbcali.ecommerceusb.mapper.ProductCategoryMapper;
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
        return ProductCategoryMapper.modelToProductCategoryResponse(pcs);
    }

    @Override
    public ProductCategoryResponse getProductCategoryById(Integer id) throws Exception {
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("ProductCategory no encontrado con el id: " + id));
        return ProductCategoryMapper.modelToProductCategoryResponse(pc);
    }

    @Override
    public ProductCategoryResponse createProductCategory(CreateProductCategoryRequest request) throws Exception {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new Exception("Categoria no encontrada con id: " + request.getCategoryId()));
        return ProductCategoryMapper.modelToProductCategoryResponse(productCategoryRepository.save(ProductCategoryMapper.createProductCategoryRequestToProductCategory(request, product, category)));
    }

    @Override
    public ProductCategoryResponse updateProductCategory(Integer id, UpdateProductCategoryRequest request) throws Exception {
        ProductCategory pc = productCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("ProductCategory no encontrado con el id: " + id));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new Exception("Categoria no encontrada con id: " + request.getCategoryId()));
        pc.setProduct(product);
        pc.setCategory(category);
        return ProductCategoryMapper.modelToProductCategoryResponse(productCategoryRepository.save(pc));
    }
}
