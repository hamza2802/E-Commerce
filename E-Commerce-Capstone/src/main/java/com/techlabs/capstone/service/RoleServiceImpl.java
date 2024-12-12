package com.techlabs.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.RoleType;
import com.techlabs.capstone.repository.RoleRepository;

import jakarta.annotation.PostConstruct; 
@Service
public class RoleServiceImpl {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void createRoles() {
        if (roleRepository.existsById(RoleType.ADMIN)) {
            return; 
        }
        
        for (RoleType roleType : RoleType.values()) {
            Role role = new Role();
            role.setRole(roleType);

            switch (roleType) {
                case ADMIN:
                    role.setDescription("Administrator role with full access to all resources.");
                    break;
                case USER:
                    role.setDescription("Regular user with limited access to basic features.");
                    break;
                case DELIVERY_AGENT:
                    role.setDescription("Delivery agent responsible for delivering products.");
                    break;
               default:
                    role.setDescription(roleType.name() + " role");
                    break;
            }

            roleRepository.save(role);
        }
    }
}
