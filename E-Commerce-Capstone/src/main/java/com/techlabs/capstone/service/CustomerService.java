package com.techlabs.capstone.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.techlabs.capstone.dto.CustomerDetailsResponseDto;

public interface CustomerService {

	Page<CustomerDetailsResponseDto> getAllCustomers(Pageable pageable);

}
