package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class RegistrationDto {

	private String email;

	private String password;
	
	private String firstname;
	
	private String lastname;


}