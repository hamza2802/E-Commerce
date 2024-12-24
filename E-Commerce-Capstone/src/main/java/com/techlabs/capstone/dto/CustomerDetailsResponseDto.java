package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CustomerDetailsResponseDto {

	private String firstName;
	private String lastName;
	private String contactNumber;
	private String alternateContactNumber;
	private String address;
	private String city;
	private String state;
	private int pincode;

}
