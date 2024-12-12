package com.techlabs.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.UserDetailsRequestDto;
import com.techlabs.capstone.dto.UserDetailsResponseDto;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.entity.UserDetails;
import com.techlabs.capstone.repository.UserDetailsRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Add user details
	public UserDetailsResponseDto addUserDetails(UserDetailsRequestDto userDetailsRequestDto) {
		// Find user by userId
		User user = userRepository.findById(userDetailsRequestDto.getUserId()).orElseThrow(
				() -> new RuntimeException("User not found with ID: " + userDetailsRequestDto.getUserId()));

		// Create and set UserDetails for the user
		UserDetails userDetails = new UserDetails();
		userDetails.setContactNumber(userDetailsRequestDto.getContactNumber());
		userDetails.setAlternateContactNumber(userDetailsRequestDto.getAlternateContactNumber());
		userDetails.setAddress(userDetailsRequestDto.getAddress());
		userDetails.setCity(userDetailsRequestDto.getCity());
		userDetails.setState(userDetailsRequestDto.getState());
		userDetails.setPincode(userDetailsRequestDto.getPincode());
		userDetails.setUser(user); // Link to the user

		// Save UserDetails
		UserDetails savedUserDetails = userDetailsRepository.save(userDetails);

		return modelMapper.map(savedUserDetails, UserDetailsResponseDto.class);
	}

	// Update user details
	public UserDetailsResponseDto updateUserDetails(int userId, UserDetailsRequestDto userDetailsRequestDto) {
		// Find existing UserDetails by userId
		UserDetails existingUserDetails = userDetailsRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("UserDetails not found for user with ID: " + userId));

		// Update the details
		existingUserDetails.setContactNumber(userDetailsRequestDto.getContactNumber());
		existingUserDetails.setAlternateContactNumber(userDetailsRequestDto.getAlternateContactNumber());
		existingUserDetails.setAddress(userDetailsRequestDto.getAddress());
		existingUserDetails.setCity(userDetailsRequestDto.getCity());
		existingUserDetails.setState(userDetailsRequestDto.getState());
		existingUserDetails.setPincode(userDetailsRequestDto.getPincode());

		// Save updated UserDetails
		UserDetails updatedUserDetails = userDetailsRepository.save(existingUserDetails);

		return modelMapper.map(updatedUserDetails, UserDetailsResponseDto.class);
	}

}
