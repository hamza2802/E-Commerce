package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer>{
	
	Optional<UserDetails> findByUser_UserId(int userId);


}
