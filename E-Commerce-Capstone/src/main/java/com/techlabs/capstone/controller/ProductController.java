package com.techlabs.capstone.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;
import com.techlabs.capstone.service.ProductImageService;
import com.techlabs.capstone.service.ProductService;

@RestController
@RequestMapping("/e-commerce/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductResponseDto> addProductWithImages(
			@RequestParam("product") String productRequestDtoJson, @RequestParam("file") MultipartFile[] files) {
		ObjectMapper objectMapper = new ObjectMapper();
		ProductRequestDto productRequestDto;
		try {
			productRequestDto = objectMapper.readValue(productRequestDtoJson, ProductRequestDto.class);
		} catch (JsonProcessingException e) {
			ProductResponseDto errorResponse = new ProductResponseDto();
			errorResponse.setImageUrls(Collections.singletonList("Invalid product data."));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			ProductResponseDto productResponseDto = productService.addProductWithImages(productRequestDto, files);
			return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
		} catch (Exception e) {
			ProductResponseDto errorResponse = new ProductResponseDto();
			errorResponse.setImageUrls(
					Collections.singletonList("Error processing product and image upload: " + e.getMessage()));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@PutMapping("/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductResponseDto> editProduct(@PathVariable int productId,
			@RequestBody ProductRequestDto productRequestDto) {
		ProductResponseDto updatedProduct = productService.editProduct(productId, productRequestDto);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam int page,
			@RequestParam int size) {
		List<ProductResponseDto> products = productService.getAllProducts(page, size);

		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDto> getProductById(@PathVariable int productId) {
		try {
			ProductResponseDto product = productService.getProductById(productId);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/active")
    public ResponseEntity<List<ProductResponseDto>> getAllProductsWithoutPagination() {
        List<ProductResponseDto> activeProducts = productService.getAllProductsWithoutPagination();

        if (activeProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }

        return new ResponseEntity<>(activeProducts, HttpStatus.OK);  
    }


}
