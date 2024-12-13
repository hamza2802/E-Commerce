package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{

	
	Optional<ProductImage> findByProduct_ProductIdAndImageLink(int productId, String imageLink);

	void deleteAllByProduct_ProductId(int productId);
	

}
