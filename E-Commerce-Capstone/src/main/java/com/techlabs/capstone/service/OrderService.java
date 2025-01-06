package com.techlabs.capstone.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techlabs.capstone.dto.AssignDeliveryAgentDto;
import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.OrderResponseDto;
import com.techlabs.capstone.entity.Order;

public interface OrderService {

	OrderResponseDto createOrder();

	OrderResponseDto getOrderById(int orderId);

	List<OrderResponseDto> getOrdersByUser(int page, int size);

	List<OrderResponseDto> getAllDeliveredOrders(int page, int size);

	List<OrderResponseDto> getAllOutForDeliveryOrders(int page, int size);

	List<OrderResponseDto> getAllPlacedOrders(int page, int size);

	List<DeliveryAgentResponseDto> getDeliveryAgentsByOrderLocation(int orderId);

	OrderResponseDto markOrderAsDelivered(int orderId);

	OrderResponseDto assignDeliveryAgentToOrder(AssignDeliveryAgentDto assignDeliveryAgentDto);

	List<OrderResponseDto> getOrdersByDeliveryAgentEmail(Pageable pageable);


}
