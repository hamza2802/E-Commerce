//package com.techlabs.capstone.service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.techlabs.capstone.dto.ProductRequestDto;
//import com.techlabs.capstone.dto.ProductResponseDto;
//import com.techlabs.capstone.entity.Product;
//import com.techlabs.capstone.entity.ProductImage;
//import com.techlabs.capstone.entity.ProductType;
//import com.techlabs.capstone.repository.ProductImageRepository;
//import com.techlabs.capstone.repository.ProductRepository;
//
//import jakarta.transaction.Transactional;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductImageRepository productImageRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
//        ProductType productType = ProductType.valueOf(productRequestDto.getCategory().toString());
//
//        Product product = modelMapper.map(productRequestDto, Product.class);
//        product.setCategory(productType);
//
//        Product savedProduct = productRepository.save(product);
//
//        if (productRequestDto.getImageLinks() != null && !productRequestDto.getImageLinks().isEmpty()) {
//            List<ProductImage> productImages = productRequestDto.getImageLinks().stream()
//                    .map(imageLink -> {
//                        ProductImage productImage = new ProductImage();
//                        productImage.setImageLink(imageLink);
//                        productImage.setProduct(savedProduct);
//                        return productImage;
//                    }).collect(Collectors.toList());
//
//            productImageRepository.saveAll(productImages);
//        }
//
//        return modelMapper.map(savedProduct, ProductResponseDto.class);
//    }
//    
//    @Override
//    public ProductResponseDto updateProduct(int productId, ProductRequestDto productRequestDto) {
//        Product existingProduct = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
//
//        // Map fields from DTO to the existing product entity
//        ProductType productType = ProductType.valueOf(productRequestDto.getCategory().toString());
//        existingProduct.setProductName(productRequestDto.getProductName());
//        existingProduct.setProductDescription(productRequestDto.getProductDescription());
//        existingProduct.setProductDiscountedPrice(productRequestDto.getProductDiscountedPrice());
//        existingProduct.setProductActualPrice(productRequestDto.getProductActualPrice());
//        existingProduct.setCategory(productType);
//
//        // Save the updated product entity
//        Product updatedProduct = productRepository.save(existingProduct);
//
//        // Return updated product response
//        return modelMapper.map(updatedProduct, ProductResponseDto.class);
//    }
//
//}
