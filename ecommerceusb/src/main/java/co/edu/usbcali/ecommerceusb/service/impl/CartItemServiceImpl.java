package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartItemRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartItemMapper;
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
        return CartItemMapper.modelToCartItemResponse(items);
    }

    @Override
    public CartItemResponse getCartItemById(Integer id) throws Exception {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("CartItem no encontrado con el id: " + id));
        return CartItemMapper.modelToCartItemResponse(item);
    }

    @Override
    public CartItemResponse createCartItem(CreateCartItemRequest request) throws Exception {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new Exception("Cart no encontrado con id: " + request.getCartId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(CartItemMapper.createCartItemRequestToCartItem(request, cart, product)));
    }

    @Override
    public CartItemResponse updateCartItem(Integer id, UpdateCartItemRequest request) throws Exception {
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception("CartItem no encontrado con el id: " + id));
        item.setQuantity(request.getQuantity());
        item.setUpdatedAt(OffsetDateTime.now());
        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(item));
    }
}
