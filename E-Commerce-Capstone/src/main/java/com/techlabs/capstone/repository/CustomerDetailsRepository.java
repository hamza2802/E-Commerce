package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.CustomerDetails;
import com.techlabs.capstone.entity.User;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Integer>{

	 Optional<CustomerDetails> findByUser(User user);
	
	 
	  Page<CustomerDetails> findByUserIsActiveTrue(Pageable pageable);


	 Optional<CustomerDetails> findByUserEmail(String email);


}
