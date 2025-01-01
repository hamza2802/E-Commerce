package com.techlabs.capstone.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;	
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.CustomerDetailsRequestDto;
import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.entity.CustomerDetails;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.CustomerDetailsRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDetailsResponseDto editCustomerDetails(CustomerDetailsRequestDto customerDetailsRequestDto) {
       
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        CustomerDetails existingCustomerDetails = customerDetailsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer details not found"));

        modelMapper.map(customerDetailsRequestDto, existingCustomerDetails);

        CustomerDetails updatedCustomerDetails = customerDetailsRepository.save(existingCustomerDetails);

        return modelMapper.map(updatedCustomerDetails, CustomerDetailsResponseDto.class);
    }
    
    @Override
    public CustomerDetailsResponseDto getCustomerByEmail() {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername(); 
        
        CustomerDetails customerDetails = customerDetailsRepository.findByUserEmail(email)
            .orElseThrow(() -> new RuntimeException("Customer not found for email: " + email));

        CustomerDetailsResponseDto responseDto = modelMapper.map(customerDetails, CustomerDetailsResponseDto.class);
        
        responseDto.setEmail(email);
        
        return responseDto;
    }

}