package com.techlabs.capstone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AssignDeliveryAgentDto {
	
	private int orderId;
	
	private String deliveryAgentEmail;

}
