package com.techlabs.capstone.dto;

import com.techlabs.capstone.entity.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
	
	private int userId;
	private String userFirstName;
	private String userLastName;
	private String email;
	private String password;
	private RoleType role; 

}
