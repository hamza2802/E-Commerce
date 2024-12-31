package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserResponseDto {

	private int userId;
	private String firstname;
	private String lastname;
	private String email;
	private String role;

}
