package com.techlabs.capstone.dto;

import com.techlabs.capstone.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserDetailsRequestDto {

	private int userDetailsId;

	private long contactNumber;

	private long alternateContactNumber;

	private String address;

	private String city;

	private String State;

	private int pincode;

	private User user;

}
