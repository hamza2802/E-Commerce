package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{

	Optional<ProductImage> findByProduct_ProductIdAndImageUrl(int productId, String imageUrl);

}
