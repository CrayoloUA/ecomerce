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
import java.util.Objects;

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
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("CartItem no encontrado con el id: %d", id)));
        return CartItemMapper.modelToCartItemResponse(item);
    }

    @Override
    public CartItemResponse createCartItem(CreateCartItemRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createCartItemRequest no puede ser nulo.");
        if (Objects.isNull(request.getCartId()) || request.getCartId() <= 0)
            throw new Exception("El campo cartId debe ser mayor a 0.");
        if (Objects.isNull(request.getProductId()) || request.getProductId() <= 0)
            throw new Exception("El campo productId debe ser mayor a 0.");
        if (Objects.isNull(request.getQuantity()) || request.getQuantity() <= 0)
            throw new Exception("El campo quantity debe ser mayor a 0.");
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new Exception("Cart no encontrado con id: " + request.getCartId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Producto no encontrado con id: " + request.getProductId()));
        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(CartItemMapper.createCartItemRequestToCartItem(request, cart, product)));
    }

    @Override
    public CartItemResponse updateCartItem(Integer id, UpdateCartItemRequest request) throws Exception {
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        if (Objects.isNull(request)) throw new Exception("El objeto updateCartItemRequest no puede ser nulo.");
        CartItem item = cartItemRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("CartItem no encontrado con el id: %d", id)));
        if (!Objects.isNull(request.getQuantity()) && request.getQuantity() > 0)
            item.setQuantity(request.getQuantity());
        item.setUpdatedAt(OffsetDateTime.now());
        return CartItemMapper.modelToCartItemResponse(cartItemRepository.save(item));
    }
}
