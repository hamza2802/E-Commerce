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

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		for (MultipartFile file : files) {
			var uploadResult = cloudinary.uploader().upload(file.getBytes(),
					ObjectUtils.asMap("resource_type", "auto"));

			String imageUrl = (String) uploadResult.get("url");

			ProductImage productImage = new ProductImage();
			productImage.setImageUrl(imageUrl);
			productImage.setProduct(product);

			productImageRepository.save(productImage);
		}
	}

}
