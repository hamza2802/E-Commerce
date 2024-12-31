package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.AdminDetails;

@Repository
public interface AdminDetailsRepository extends JpaRepository<AdminDetails, Integer>{
	
	Optional<AdminDetails> findByUser_UserId(int userId);


}
