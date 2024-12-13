package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;

public interface ProductService {

	ProductResponseDto addProduct(ProductRequestDto productRequestDto);

	ProductResponseDto updateProduct(int productId, ProductRequestDto productRequestDto);

}
