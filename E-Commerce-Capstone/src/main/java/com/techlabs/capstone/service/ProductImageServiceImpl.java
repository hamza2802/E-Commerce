package com.techlabs.capstone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.entity.Product;
import com.techlabs.capstone.entity.ProductImage;
import com.techlabs.capstone.repository.ProductImageRepository;
import com.techlabs.capstone.repository.ProductRepository;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository; // Inject ProductRepository to fetch Product by ID

    @Override
    public void updateProductImages(int productId, List<String> newImageLinks) {
        // Fetch the product by its ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // First, delete all existing images for the given product
        productImageRepository.deleteAllByProduct_ProductId(productId);

        // If new images are provided, add them
        if (newImageLinks != null && !newImageLinks.isEmpty()) {
            List<ProductImage> productImages = newImageLinks.stream()
                    .map(imageLink -> {
                        ProductImage productImage = new ProductImage();
                        productImage.setImageLink(imageLink);
                        productImage.setProduct(product);  // Associate the product with the image
                        return productImage;
                    }).collect(Collectors.toList());

            // Save new images
            productImageRepository.saveAll(productImages);
        }
    }

    @Override
    public void deleteImage(int productId, String imageLink) {
        // Find the product image based on the product ID and image link
        ProductImage productImage = productImageRepository.findByProduct_ProductIdAndImageLink(productId, imageLink)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Delete the image
        productImageRepository.delete(productImage);
    }
}
