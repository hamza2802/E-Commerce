package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ProductRequestDto {

	private String productName;
    private String category;
    private int stock;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

}
