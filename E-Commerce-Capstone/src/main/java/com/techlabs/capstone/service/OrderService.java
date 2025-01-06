package com.techlabs.capstone.service;

import java.util.List;

import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.OrderResponseDto;

public interface OrderService {

	OrderResponseDto createOrder();

	OrderResponseDto getOrderById(int orderId);

	List<OrderResponseDto> getOrdersByUser(int page, int size);

	List<OrderResponseDto> getAllDeliveredOrders(int page, int size);

	List<OrderResponseDto> getAllOutForDeliveryOrders(int page, int size);

	List<OrderResponseDto> getAllPlacedOrders(int page, int size);

	List<DeliveryAgentResponseDto> getDeliveryAgentsByOrderLocation(int orderId);

	OrderResponseDto assignDeliveryAgentToOrder(int orderId, int deliveryAgentId);

	OrderResponseDto markOrderAsDelivered(int orderId);

}
