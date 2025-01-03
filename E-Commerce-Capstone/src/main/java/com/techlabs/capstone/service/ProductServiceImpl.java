package com.techlabs.capstone.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techlabs.capstone.dto.ProductRequestDto;
import com.techlabs.capstone.dto.ProductResponseDto;
import com.techlabs.capstone.entity.Product;
import com.techlabs.capstone.entity.ProductImage;
import com.techlabs.capstone.repository.ProductImageRepository;
import com.techlabs.capstone.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponseDto addProductWithImages(ProductRequestDto productRequestDto, MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            throw new IllegalArgumentException("At least one image must be uploaded.");
        }

        Product product = modelMapper.map(productRequestDto, Product.class);

        // Set isActive to true when adding a new product
        product.setActive(true);

        Product savedProduct = productRepository.save(product);

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                String imageUrl = (String) uploadResult.get("url");
                imageUrls.add(imageUrl);

                ProductImage productImage = new ProductImage();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct(savedProduct);
                productImageRepository.save(productImage);
            } catch (IOException e) {
                throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
            }
        }

        ProductResponseDto productResponseDto = modelMapper.map(savedProduct, ProductResponseDto.class);
        productResponseDto.setImageUrls(imageUrls);

        return productResponseDto;
    }


    @Override
    public ProductResponseDto editProduct(int productId, ProductRequestDto productRequestDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(productRequestDto.getProductName());
        existingProduct.setCategory(productRequestDto.getCategory());
        existingProduct.setStock(productRequestDto.getStock());
        existingProduct.setProductDescription(productRequestDto.getProductDescription());
        existingProduct.setProductDiscountedPrice(productRequestDto.getProductDiscountedPrice());
        existingProduct.setProductActualPrice(productRequestDto.getProductActualPrice());
        Product updatedProduct = productRepository.save(existingProduct);

        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override	
    public List<ProductResponseDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // Fetch only products with isActive = true
        Page<Product> productPage = productRepository.findByIsActiveTrue(pageable);

        List<ProductResponseDto> productResponseDtos = productPage.stream()
                .map(product -> {
                    ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
                    List<String> imageUrls = product.getProductImages().stream()
                            .map(ProductImage::getImageUrl)
                            .collect(Collectors.toList());
                    productResponseDto.setImageUrls(imageUrls);
                    return productResponseDto;
                })
                .collect(Collectors.toList());

        return productResponseDtos;
    }


   

    @Override
    public ProductResponseDto getProductById(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        List<String> imageUrls = product.getProductImages().stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());
        productResponseDto.setImageUrls(imageUrls);

        return productResponseDto;
    }
    
    @Override
    public List<ProductResponseDto> getAllProductsWithoutPagination() {
        // Fetch all products with isActive = true
        List<Product> products = productRepository.findByIsActiveTrue();

        // Convert the products to ProductResponseDto and include image URLs
        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(product -> {
                    ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
                    List<String> imageUrls = product.getProductImages().stream()
                            .map(ProductImage::getImageUrl)
                            .collect(Collectors.toList());
                    productResponseDto.setImageUrls(imageUrls);
                    return productResponseDto;
                })
                .collect(Collectors.toList());

        return productResponseDtos;
    }


    @Override
    public void deleteProduct(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(false);
        productRepository.save(product);
    }
}
