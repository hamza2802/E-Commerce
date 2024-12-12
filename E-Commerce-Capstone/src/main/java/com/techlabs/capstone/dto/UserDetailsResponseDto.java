package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserDetailsResponseDto {

	private int userDetailsId;

	private long contactNumber;

	private long alternateContactNumber;

	private String address;

	private String city;

	private String State;

	private int pincode;

	private int userId;

}
