package com.techlabs.capstone.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class OrderResponseDto {

    private int orderId;
    private double totalAmount;
    private String status;
    private Date orderDate;
    private List<OrderItemResponseDto> orderItems;
    private DeliveryAgentResponseDto deliveryAgent;
    private CustomerDetailsResponseDto customerDetails;
    
}