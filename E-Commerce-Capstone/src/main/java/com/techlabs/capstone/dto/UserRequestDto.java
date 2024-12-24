package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {

	private int userId;
	private String userFirstName;
	private String userLastName;
	private String email;
	private String password;
	private String role;
	
	//for delivery agent details
	private String vehicleType; 
    private String vehicleNumber;
    private String deliveryZone;
    private long contactNumber;
}