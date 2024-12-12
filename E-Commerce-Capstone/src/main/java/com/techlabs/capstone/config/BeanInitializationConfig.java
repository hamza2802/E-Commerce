package com.techlabs.capstone.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.techlabs.capstone.service.RoleServiceImpl;
import com.techlabs.capstone.service.UserServiceImpl;

import jakarta.annotation.PostConstruct;

@Configuration
public class BeanInitializationConfig {

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostConstruct
    public void initializeServices() {
        roleServiceImpl.createRoles();  

        // Then, initialize user service if needed
        userServiceImpl.initAdminUser(); // Call the initAdminUser() method manually
    }
}


