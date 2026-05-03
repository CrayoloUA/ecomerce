package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;
import co.edu.usbcali.ecommerceusb.repository.CartItemRepository;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.ProductRepository;
import co.edu.usbcali.ecommerceusb.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CartItemResponse> getCartItems() {
        List<CartItem> items = cartItemRepository.findAll();
        if (items.isEmpty()) return List.of();
        return items.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CartItemResponse getCartItemById(Integer id) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("CartItem no encontrado con id: " + id));
        return toResponse(item);
    }

    @Override
    public CartItemResponse createCartItem(CreateCartItemRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El request no puede ser nulo");
        if (Objects.isNull(request.getCartId()) || request.getCartId() <= 0)
            throw new Exception("El campo cartId debe ser mayor a 0");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0");
        if (Objects.isNull(request.getQuantity()) || request.getQuantity() <= 0)
            throw new Exception("El campo quantity debe ser mayor a 0");

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new Exception("Cart no encontrado con id: " + request.getCartId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Product no encontrado con id: " + request.getProductId()));

        CartItem item = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        return toResponse(cartItemRepository.save(item));
    }

    private CartItemResponse toResponse(CartItem i) {
        return CartItemResponse.builder()
                .id(i.getId())
                .cartId(i.getCart().getId())
                .productId(i.getProduct().getId())
                .quantity(i.getQuantity())
                .createdAt(i.getCreatedAt())
                .updatedAt(i.getUpdatedAt())
                .build();
    }
}
