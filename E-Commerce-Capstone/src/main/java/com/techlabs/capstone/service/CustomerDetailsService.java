package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.CustomerDetailsRequestDto;
import com.techlabs.capstone.dto.CustomerDetailsResponseDto;

public interface CustomerDetailsService {

//	CustomerDetailsResponseDto editCustomerDetails(String email, CustomerDetailsRequestDto customerDetailsRequestDto);

	CustomerDetailsResponseDto editCustomerDetails(CustomerDetailsRequestDto customerDetailsRequestDto);


}
