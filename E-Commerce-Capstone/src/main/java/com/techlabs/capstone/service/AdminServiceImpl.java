package com.techlabs.capstone.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.entity.AdminDetails;
import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.AdminDetailsRepository;
import com.techlabs.capstone.repository.RoleRepository;
import com.techlabs.capstone.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
@Order(2)
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AdminDetailsRepository adminDetailsRepository; // Repository for AdminDetails

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void initAdminUser() {
		Optional<User> existingAdminUser = userRepository.findByEmail("admin@gmail.com");
		if (existingAdminUser.isPresent()) {
			return;
		}

		List<Role> roles = roleRepository.findByRole("ROLE_ADMIN");
		if (roles.isEmpty()) {
			throw new RuntimeException("Admin role not found");
		}

		User adminUser = new User();
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword(passwordEncoder.encode("Admin@123"));
		adminUser.setActive(true);

		adminUser.setRoles(roles);

		// Create the AdminDetails object (firstName, lastName)
		AdminDetails adminDetails = new AdminDetails();
		adminDetails.setFirstName("Admin");
		adminDetails.setLastName("User");
		adminDetails.setUser(adminUser); // Link AdminDetails to User

		// Save the admin user
		userRepository.save(adminUser);

		// Save the adminDetails object, which is related to the admin user
		adminDetailsRepository.save(adminDetails);
	}
}
