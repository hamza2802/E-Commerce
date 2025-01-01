package com.techlabs.capstone.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CartResponseDto {
    
    private int cartId;
    private double totalAmount;
    private List<CartItemResponseDto> cartItems;
}
