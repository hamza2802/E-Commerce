package com.techlabs.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.entity.CustomerDetails;
import com.techlabs.capstone.repository.CustomerDetailsRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	    @Autowired
	    private CustomerDetailsRepository customerDetailsRepository;

	    @Autowired
	    private ModelMapper modelMapper;

	    @Override
	    public Page<CustomerDetailsResponseDto> getAllCustomers(Pageable pageable) {
	        Page<CustomerDetails> customerDetailsPage = customerDetailsRepository.findByUserIsActiveTrue(pageable);

	        return customerDetailsPage.map(customerDetails -> {
	            CustomerDetailsResponseDto responseDto = modelMapper.map(customerDetails, CustomerDetailsResponseDto.class);
	            String email = customerDetails.getUser().getEmail();
	            responseDto.setEmail(email);
	            return responseDto;
	        });
	    }
	    
	  


}
