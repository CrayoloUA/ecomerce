package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.dto.UpdateProductRequest;
import co.edu.usbcali.ecommerceusb.mapper.ProductMapper;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return List.of();
        return ProductMapper.modelToProductResponse(products);
    }

    @Override
    public ProductResponse getProductById(Integer id) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado con el id: " + id));
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        return ProductMapper.modelToProductResponse(productRepository.save(ProductMapper.createProductRequestToProduct(request)));
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest request) throws Exception {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado con el id: " + id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setAvailable(request.getAvailable());
        product.setUpdatedAt(OffsetDateTime.now());
        return ProductMapper.modelToProductResponse(productRepository.save(product));
    }
}
