package com.techlabs.capstone.dto;

import java.util.List;

import com.techlabs.capstone.entity.ProductType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ProductResponseDto {

	private int productId;

	private String productName;

	private String productDescription;

	private Double productDiscountedPrice;

	private Double productActualPrice;
	
	private ProductType category;  
	
    private List<String> imageLinks; 

}
