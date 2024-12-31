package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CartItemRequestDto {

    private int productId;
    private int quantity;
    
}