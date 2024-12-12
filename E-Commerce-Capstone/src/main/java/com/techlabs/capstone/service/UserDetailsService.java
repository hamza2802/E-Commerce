package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.UserDetailsRequestDto;
import com.techlabs.capstone.dto.UserDetailsResponseDto;

public interface UserDetailsService {

	UserDetailsResponseDto saveOrUpdateUserDetails(int userId, UserDetailsRequestDto userDetailsRequestDto);

}
