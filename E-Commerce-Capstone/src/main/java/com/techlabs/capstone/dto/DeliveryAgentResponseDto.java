package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DeliveryAgentResponseDto {
	
    private String email;
    private String firstname;
    private String lastname;
    private String contactNumber;
    private String vehicleType;
    private String vehicleNumber;
    private String deliveryZone;


}
