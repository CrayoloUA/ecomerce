// tarea
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemMapper {

    public static CartItemResponse modelToCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart() != null ? cartItem.getCart().getId() : null)
                .productId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null)
                .qty(cartItem.getQty())
                .build();
    }

    public static List<CartItemResponse> modelToCartItemResponse(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemMapper::modelToCartItemResponse)
                .collect(Collectors.toList());
    }

    public static CartItem createCartItemRequestToCartItem(CreateCartItemRequest request, Cart cart, Product product) {
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .qty(request.getQty())
                .build();
    }
}
