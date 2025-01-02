package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderItemResponseDto {

    private int orderItemId;
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
    
}