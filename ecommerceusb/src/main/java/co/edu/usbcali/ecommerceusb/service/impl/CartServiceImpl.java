// tarea
package co.edu.usbcali.ecommerceusb.service.impl;

import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.mapper.CartMapper;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;
import co.edu.usbcali.ecommerceusb.repository.CartRepository;
import co.edu.usbcali.ecommerceusb.repository.UserRepository;
import co.edu.usbcali.ecommerceusb.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        if (id == null || id <= 0) throw new Exception("Debe ingresar un id válido");
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new Exception(String.format("Cart no encontrado con el id: %d", id)));
        return CartMapper.modelToCartResponse(cart);
    }

    @Override
    public CartResponse createCart(CreateCartRequest request) throws Exception {
        if (Objects.isNull(request)) throw new Exception("El objeto createCartRequest no puede ser nulo.");
        if (Objects.isNull(request.getUserId()) || request.getUserId() <= 0)
            throw new Exception("El campo userId debe ser mayor a 0.");
        if (Objects.isNull(request.getStatus()) || request.getStatus().isBlank())
            throw new Exception("El campo status no puede ser nulo.");
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Usuario no encontrado con id: " + request.getUserId()));
        Cart cart = CartMapper.createCartRequestToCart(request, user);
        return CartMapper.modelToCartResponse(cartRepository.save(cart));
    }
}
