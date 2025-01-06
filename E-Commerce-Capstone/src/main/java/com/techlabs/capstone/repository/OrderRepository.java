package com.techlabs.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.DeliveryAgentDetails;
import com.techlabs.capstone.entity.Order;
import com.techlabs.capstone.entity.OrderStatus;
import com.techlabs.capstone.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	Page<Order> findByUser(User user, Pageable pageable);

	Page<Order> findByStatus(OrderStatus placed, Pageable pageable);

	Page<Order> findByDeliveryAgent(DeliveryAgentDetails deliveryAgentDetails, Pageable pageable);

}
