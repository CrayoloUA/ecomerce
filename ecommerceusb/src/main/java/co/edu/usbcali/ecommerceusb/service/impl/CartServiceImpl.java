package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.dto.UpdateCartRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartResponse> getCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) return List.of();
        return CartMapper.modelToCartResponse(carts);
    }

    @Override
    public CartResponse getCartById(Integer id) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception("Cart no encontrado con el id: " + id));
        return CartMapper.modelToCartResponse(cart);
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + request.getUserId()));
        return CartMapper.modelToCartResponse(cartRepository.save(CartMapper.createCartRequestToCart(request, user)));
    }

    @Override
    public CartResponse updateCart(Integer id, UpdateCartRequest request) throws Exception {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception("Cart no encontrado con el id: " + id));
        cart.setStatus(Cart.CartStatus.valueOf(request.getStatus()));
        cart.setUpdatedAt(OffsetDateTime.now());
        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }
}
