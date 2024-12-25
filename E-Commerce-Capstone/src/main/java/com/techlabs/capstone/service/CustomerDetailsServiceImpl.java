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
        // Extract email from the authenticated user (from the JWT token)
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername(); // Assuming username is the email

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Find the existing CustomerDetails for the user
        CustomerDetails existingCustomerDetails = customerDetailsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer details not found"));

        // Map the updated details
        modelMapper.map(customerDetailsRequestDto, existingCustomerDetails);

        // Save the updated customer details
        CustomerDetails updatedCustomerDetails = customerDetailsRepository.save(existingCustomerDetails);

        // Map the updated customer details to the response DTO
        return modelMapper.map(updatedCustomerDetails, CustomerDetailsResponseDto.class);
    }
}