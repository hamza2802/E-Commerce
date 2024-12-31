package com.techlabs.capstone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.DeliveryAgentDetails;

@Repository
public interface DeliveryAgentDetailsRepository extends JpaRepository<DeliveryAgentDetails, Integer> {
	
	Page<DeliveryAgentDetails> findByUserIsActiveTrue(Pageable pageable);
}
