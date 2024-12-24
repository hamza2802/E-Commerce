package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.CustomerDetailsRequestDto;
import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.service.CustomerDetailsService;
import com.techlabs.capstone.service.CustomerService;

@RestController
@RequestMapping("/e-commerce/customers")
public class CustomerController {

    @Autowired
    private CustomerDetailsService customerDetailsService;
    
    @Autowired
    private CustomerService customerService;

    @PutMapping("/edit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerDetailsResponseDto> editCustomerDetails(
            @RequestBody CustomerDetailsRequestDto customerDetailsRequestDto) {

        // Extract email from request DTO (as email is part of request body)
        String email = customerDetailsRequestDto.getEmail();

        // Call the service to edit customer details by email
        CustomerDetailsResponseDto updatedCustomerDetails = customerDetailsService.editCustomerDetails(email, customerDetailsRequestDto);
        
        // Return the updated customer details as a response
        return ResponseEntity.ok(updatedCustomerDetails);
    }
    

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CustomerDetailsResponseDto>> getAllCustomers(
    		 @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "size", defaultValue = "10") int size) {
        
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CustomerDetailsResponseDto> customers = customerService.getAllCustomers(pageRequest);

        // Return the paginated list of customers wrapped in ResponseEntity
        return ResponseEntity.ok(customers);
    }
    
    
}
