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
import java.util.Objects;

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
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Producto no encontrado con el id: %d", id)));
        return ProductMapper.modelToProductResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createProductRequest no puede ser nulo.");
        if (Objects.isNull(request.getName()) || request.getName().isBlank())
            throw new Exception("El campo name no puede ser nulo.");
        if (Objects.isNull(request.getPrice())) throw new Exception("El campo price no puede ser nulo.");
        if (Objects.isNull(request.getAvailable())) throw new Exception("El campo available no puede ser nulo.");
        return ProductMapper.modelToProductResponse(productRepository.save(ProductMapper.createProductRequestToProduct(request)));
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateProductRequest no puede ser nulo.");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Producto no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getName()) && !request.getName().isBlank())
            product.setName(request.getName());
        if (!Objects.isNull(request.getDescription()) && !request.getDescription().isBlank())
            product.setDescription(request.getDescription());
        if (!Objects.isNull(request.getPrice()))
            product.setPrice(request.getPrice());
        if (!Objects.isNull(request.getAvailable()))
            product.setAvailable(request.getAvailable());
        product.setUpdatedAt(OffsetDateTime.now());
        return ProductMapper.modelToProductResponse(productRepository.save(product));
    }
}
