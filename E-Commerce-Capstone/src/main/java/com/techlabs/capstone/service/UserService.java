package com.techlabs.capstone.service;

import org.springframework.data.domain.Page;

import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.UserRequestDto;
import com.techlabs.capstone.dto.UserResponseDto;

public interface UserService {

	public UserResponseDto createNewUser(UserRequestDto userRequestDto);

	public UserResponseDto addNewDeliveryAgent(UserRequestDto userRequestDto);

	public UserResponseDto updateUser(int userId, UserRequestDto userRequestDto);

	public UserResponseDto verifyUserCredentials(String email, String password);
	
	Page<UserResponseDto> getAllUsersWithRoleUser(int page, int size);

	Page<DeliveryAgentResponseDto> getAllDeliveryAgents(int page, int size);


}
