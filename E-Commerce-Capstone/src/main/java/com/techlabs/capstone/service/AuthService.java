package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.JwtAuthResponse;
import com.techlabs.capstone.dto.LoginDto;
import com.techlabs.capstone.dto.RegistrationDto;
import com.techlabs.capstone.dto.UserResponseDto;
import com.techlabs.capstone.entity.User;

public interface AuthService {
	
	
	JwtAuthResponse login(LoginDto loginDto);

	UserResponseDto register(RegistrationDto registration);

}