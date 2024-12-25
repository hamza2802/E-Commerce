package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{

	
	Optional<ProductImage> findByProduct_ProductIdAndImageUrl(int productId, String imageUrl);

	

}
