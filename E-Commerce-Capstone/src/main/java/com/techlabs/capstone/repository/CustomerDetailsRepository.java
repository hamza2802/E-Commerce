package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.CustomerDetails;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Integer>{
	
	Optional<CustomerDetails> findByUser_UserId(int userId);


}
