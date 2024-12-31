package com.techlabs.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
