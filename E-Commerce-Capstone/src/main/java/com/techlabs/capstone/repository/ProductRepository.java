package com.techlabs.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
