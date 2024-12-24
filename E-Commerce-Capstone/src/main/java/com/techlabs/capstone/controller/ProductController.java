//package com.techlabs.capstone.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.techlabs.capstone.dto.ProductRequestDto;
//import com.techlabs.capstone.dto.ProductResponseDto;
//import com.techlabs.capstone.service.ProductImageService;
//import com.techlabs.capstone.service.ProductService;
//
//@RestController
//@RequestMapping("/e-commerce/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//    
//    @Autowired
//    private ProductImageService productImageService;
//    
//    @PostMapping("/add")
//    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
//        ProductResponseDto responseDto = productService.addProduct(productRequestDto);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @PutMapping("/update/{productId}")
//    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable int productId, @RequestBody ProductRequestDto productRequestDto) {
//        ProductResponseDto responseDto = productService.updateProduct(productId, productRequestDto);
//        return ResponseEntity.ok(responseDto);
//    }
//    
//    @PutMapping("/images/update/{productId}")
//    public ResponseEntity<Void> updateProductImages(
//            @PathVariable int productId,
//            @RequestBody List<String> imageLinks) {
//        productImageService.updateProductImages(productId, imageLinks);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/images/{productId}")
//    public ResponseEntity<Void> deleteProductImage(
//            @PathVariable int productId,
//            @RequestParam String imageLink) {
//        productImageService.deleteImage(productId, imageLink);
//        return ResponseEntity.noContent().build();
//    }
//}
