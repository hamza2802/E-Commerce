package com.techlabs.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	List<Product> findByCategory(String category);

	Page<Product> findByIsActiveTrue(Pageable pageable);

	List<Product> findByIsActiveTrue();

}
