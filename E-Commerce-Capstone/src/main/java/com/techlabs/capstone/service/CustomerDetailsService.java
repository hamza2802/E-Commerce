package com.techlabs.capstone.service;

import com.techlabs.capstone.dto.CustomerDetailsRequestDto;
import com.techlabs.capstone.dto.CustomerDetailsResponseDto;

public interface CustomerDetailsService {


	CustomerDetailsResponseDto editCustomerDetails(CustomerDetailsRequestDto customerDetailsRequestDto);

	CustomerDetailsResponseDto getCustomerByEmail();


}
