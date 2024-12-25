package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;
import com.techlabs.capstone.service.ProductImageService;
import com.techlabs.capstone.service.ProductService;

@RestController
@RequestMapping("/e-commerce/products")
public class ProductController {

	@Autowired
	private ProductImageService productImageService;

	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
		ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);

		return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);

	}
	
	 @PutMapping("/{productId}")
	 @PreAuthorize("hasRole('ADMIN')")
	    public ResponseEntity<ProductResponseDto> editProduct(@PathVariable int productId, 
	                                                          @RequestBody ProductRequestDto productRequestDto) {
	        ProductResponseDto updatedProduct = productService.editProduct(productId, productRequestDto);
	        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	    }

	@PostMapping("/{productId}/upload-images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadImages(@PathVariable int productId, 
                                               @RequestParam("file") MultipartFile[] files) {
        try {
            productImageService.uploadImages(productId, files);
            return ResponseEntity.ok("Images uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading images: " + e.getMessage());
        }
    }

}
