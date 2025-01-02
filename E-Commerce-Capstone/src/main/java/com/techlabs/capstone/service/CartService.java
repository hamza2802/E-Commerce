package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.CartItemRequestDto;
import com.techlabs.capstone.dto.CartItemResponseDto;
import com.techlabs.capstone.dto.CartResponseDto;
import com.techlabs.capstone.entity.Cart;

public interface CartService {

    CartResponseDto getCart();

    void removeCartItemFromCart(int cartItemId);

    double getCartTotalAmount();

    CartItemResponseDto addCartItemToCart(CartItemRequestDto cartItemRequestDto);

	void updateCartItemQuantity(int cartItemId, int quantity);

}
