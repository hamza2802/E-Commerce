package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;

public interface ProductService {

	ProductResponseDto addProduct(ProductRequestDto productRequestDto);

	ProductResponseDto editProduct(int productId, ProductRequestDto productRequestDto);


}
