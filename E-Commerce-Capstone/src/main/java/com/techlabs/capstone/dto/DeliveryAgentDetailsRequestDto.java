package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DeliveryAgentDetailsRequestDto {
	
	private String firstName;
    private String lastName;
    private String contactNumber;
    private String vehicleType;
    private String vehicleNumber;
    private String deliveryZone;

}
