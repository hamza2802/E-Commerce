package com.techlabs.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
@Order(1)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * This method ensures that the default roles (admin, customer, delivery agent) are created 
     * if they do not already exist in the database.
     */
    @PostConstruct
    public void createRoles() {
        // Check if roles already exist by their names
        if (roleRepository.existsById("ROLE_ADMIN") && roleRepository.existsById("ROLE_CUSTOMER") && roleRepository.existsById("ROLE_DELIVERY_AGENT")) {
            return; // Skip role creation if they already exist
        }

        // Create the "ROLE_ADMIN" role
        Role adminRole = new Role(); 
        adminRole.setRole("ROLE_ADMIN");
        adminRole.setDescription("Administrator role with full access to all resources.");
        roleRepository.save(adminRole);

        // Create the "ROLE_CUSTOMER" role
        Role customerRole = new Role();
        customerRole.setRole("ROLE_CUSTOMER");
        customerRole.setDescription("Regular customer with access to purchase and view orders.");
        roleRepository.save(customerRole);

        // Create the "ROLE_DELIVERY_AGENT" role
        Role deliveryAgentRole = new Role();
        deliveryAgentRole.setRole("ROLE_DELIVERY_AGENT");
        deliveryAgentRole.setDescription("Delivery agent responsible for delivering products.");
        roleRepository.save(deliveryAgentRole);
    }
}
