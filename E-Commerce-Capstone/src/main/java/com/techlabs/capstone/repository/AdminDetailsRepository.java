package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.AdminDetails;

public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Integer>{
	
	Optional<AdminDetails> findByUser_UserId(int userId);


}
