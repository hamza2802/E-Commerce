package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CartItemResponseDto {
    
	private int cartItemId;
    private int productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private double totalPrice;
}
