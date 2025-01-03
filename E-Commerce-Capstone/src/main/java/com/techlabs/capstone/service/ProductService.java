package com.techlabs.capstone.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;

public interface ProductService {

	ProductResponseDto editProduct(int productId, ProductRequestDto productRequestDto);

	ProductResponseDto getProductById(int productId);

	List<ProductResponseDto> getAllProducts(int page, int size);

	ProductResponseDto addProductWithImages(ProductRequestDto productRequestDto, MultipartFile[] files)
			throws IOException;

	void deleteProduct(int productId);


}
