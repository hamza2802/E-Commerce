package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.CartItemRequestDto;
import com.techlabs.capstone.entity.Cart;
import com.techlabs.capstone.entity.CartItem;

public interface CartService {

    Cart getCart();

    void removeCartItemFromCart(int cartItemId);

    CartItem updateCartItemQuantity(int cartItemId, CartItemRequestDto cartItemRequestDto);

    double getCartTotalAmount();

    CartItem addCartItemToCart(CartItemRequestDto cartItemRequestDto);
}
