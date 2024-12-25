package com.techlabs.capstone.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techlabs.capstone.entity.Product;
import com.techlabs.capstone.entity.ProductImage;
import com.techlabs.capstone.repository.ProductImageRepository;
import com.techlabs.capstone.repository.ProductRepository;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository; 

    @Autowired
    private ProductImageRepository productImageRepository;
 
    @Override
    public void uploadImages(int productId, MultipartFile[] files) throws IOException {
        // Fetch the product based on the provided product ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Iterate over each file and upload it to Cloudinary
        for (MultipartFile file : files) {
            // Upload each file to Cloudinary
            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

            // Get the image URL from the upload result
            String imageUrl = (String) uploadResult.get("url");

            // Create a new ProductImage entity
            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(imageUrl);
            productImage.setProduct(product); // Associate the image with the product

            // Save the ProductImage entity to the database
            productImageRepository.save(productImage);
        }
    }

	
}
