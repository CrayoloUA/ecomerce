package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CreateProductRequest;
import co.edu.usbcali.ecommerceusb.dto.ProductResponse;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return List.of();
        return products.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Product no encontrado con id: " + id));
        return toResponse(product);
    }

    @Override
    public ProductResponse createProduct(CreateProductRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getName()) || request.getName().isBlank())
            throw new Exception("El campo name no puede ser nulo");
        if (Objects.isNull(request.getPrice()) || request.getPrice().doubleValue() <= 0)
            throw new Exception("El campo price debe ser mayor a 0");
        if (Objects.isNull(request.getAvailable()))
            throw new Exception("El campo available no puede ser nulo");

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .available(request.getAvailable())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        return toResponse(productRepository.save(product));
    }

    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .price(p.getPrice())
                .available(p.getAvailable())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
