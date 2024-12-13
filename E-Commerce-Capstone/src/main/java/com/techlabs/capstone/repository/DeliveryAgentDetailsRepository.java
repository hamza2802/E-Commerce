package com.techlabs.capstone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.capstone.entity.DeliveryAgentDetails;
import com.techlabs.capstone.entity.User;

public interface DeliveryAgentDetailsRepository extends JpaRepository<DeliveryAgentDetails, Integer> {

	Optional<DeliveryAgentDetails> findByUser(User user);

	

}
