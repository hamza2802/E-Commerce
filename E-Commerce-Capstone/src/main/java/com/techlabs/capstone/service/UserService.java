package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.UserRequestDto;
import com.techlabs.capstone.dto.UserResponseDto;

public interface UserService {

	public UserResponseDto createNewUser(UserRequestDto userRequestDto);

	public UserResponseDto addNewDeliveryAgent(UserRequestDto userRequestDto);

	public UserResponseDto updateUser(int userId, UserRequestDto userRequestDto);

	public UserResponseDto verifyUserCredentials(String email, String password);

}
