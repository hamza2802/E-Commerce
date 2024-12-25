package com.techlabs.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;
import com.techlabs.capstone.entity.Product;
import com.techlabs.capstone.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductResponseDto addProduct(ProductRequestDto productRequestDTO) {

		Product product = modelMapper.map(productRequestDTO, Product.class);

		Product savedProduct = productRepository.save(product);

		return modelMapper.map(savedProduct, ProductResponseDto.class);
	}

	@Override
	public ProductResponseDto editProduct(int productId, ProductRequestDto productRequestDto) {
		// Fetch the product by ID
		Product existingProduct = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Update the product with the new data
		existingProduct.setProductName(productRequestDto.getProductName());
		existingProduct.setCategory(productRequestDto.getCategory());
		existingProduct.setStock(productRequestDto.getStock());
		existingProduct.setProductDescription(productRequestDto.getProductDescription());
		existingProduct.setProductDiscountedPrice(productRequestDto.getProductDiscountedPrice());
		existingProduct.setProductActualPrice(productRequestDto.getProductActualPrice());

		// Save the updated product
		Product updatedProduct = productRepository.save(existingProduct);

		// Return the updated product as a response
		return modelMapper.map(updatedProduct, ProductResponseDto.class);
	}
}
