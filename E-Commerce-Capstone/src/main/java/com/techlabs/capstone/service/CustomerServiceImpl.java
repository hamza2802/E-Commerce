package com.techlabs.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.entity.CustomerDetails;
import com.techlabs.capstone.repository.CustomerDetailsRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	    @Autowired
	    private CustomerDetailsRepository customerDetailsRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private ModelMapper modelMapper;

	    @Override
	    public Page<CustomerDetailsResponseDto> getAllCustomers(Pageable pageable) {
	        // Fetch paginated customer details where user is active
	        Page<CustomerDetails> customerDetailsPage = customerDetailsRepository.findByUserIsActiveTrue(pageable);

	        // Convert each customer to a response DTO, including the email
	        return customerDetailsPage.map(customerDetails -> {
	            CustomerDetailsResponseDto responseDto = modelMapper.map(customerDetails, CustomerDetailsResponseDto.class);
	            String email = customerDetails.getUser().getEmail(); // Get email from the User entity
	            responseDto.setEmail(email); // Set email in the response DTO
	            return responseDto;
	        });
	    }


}
