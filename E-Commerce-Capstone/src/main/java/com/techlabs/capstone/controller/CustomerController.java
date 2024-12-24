package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.CustomerDetailsRequestDto;
import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.service.CustomerDetailsService;

@RestController
@RequestMapping("/e-commerce/customers")
public class CustomerController {

    @Autowired
    private CustomerDetailsService customerService;

    // Endpoint to edit customer details
    @PutMapping("/edit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerDetailsResponseDto> editCustomerDetails(
            @RequestBody CustomerDetailsRequestDto customerDetailsRequestDto) {

        // Extract email from request DTO (as email is part of request body)
        String email = customerDetailsRequestDto.getEmail();

        // Call the service to edit customer details by email
        CustomerDetailsResponseDto updatedCustomerDetails = customerService.editCustomerDetails(email, customerDetailsRequestDto);
        
        // Return the updated customer details as a response
        return ResponseEntity.ok(updatedCustomerDetails);
    }
}
