package com.techlabs.capstone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.UserRequestDto;
import com.techlabs.capstone.dto.UserResponseDto;
import com.techlabs.capstone.entity.DeliveryAgentDetails;
import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.RoleType;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.DeliveryAgentDetailsRepository;
import com.techlabs.capstone.repository.RoleRepository;
import com.techlabs.capstone.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
@DependsOn("roleServiceImpl")  
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DeliveryAgentDetailsRepository deliveryAgentRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ModelMapper modelMapper;

	@PostConstruct
	public void initAdminUser() {
		Optional<User> existingAdminUser = userRepository.findByEmail("admin@gmail.com");
		if (existingAdminUser.isPresent()) {
			return;
		}

		User adminUser = new User();
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName("admin");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setUserPassword("admin@pass"); 

		Optional<Role> roleOptional = roleRepository.findById(RoleType.ADMIN);
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Admin role not found");
		}

		Role adminRole = roleOptional.get();
		adminUser.setRole(adminRole);

		userRepository.save(adminUser);
	}

	@Override
	public UserResponseDto createNewUser(UserRequestDto userRequestDto) {
		Optional<Role> roleOptional = roleRepository.findById(RoleType.USER);
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Role User not found");
		}

		Role role = roleOptional.get();
		User user = modelMapper.map(userRequestDto, User.class);
		user.setUserPassword(userRequestDto.getPassword());
		user.setRole(role);

		User savedUser = userRepository.save(user);
		return modelMapper.map(savedUser, UserResponseDto.class);
	}

	@Override
	public UserResponseDto addNewDeliveryAgent(UserRequestDto userRequestDto) {
	    
	    Optional<Role> roleOptional = roleRepository.findById(RoleType.DELIVERY_AGENT);
	    if (!roleOptional.isPresent()) {
	        throw new RuntimeException("Role DELIVERY_AGENT not found");
	    }

	    
	    User user = modelMapper.map(userRequestDto, User.class);
	    user.setRole(roleOptional.get());

	   
	    User savedUser = userRepository.save(user);

	 
	    DeliveryAgentDetails deliveryAgent = modelMapper.map(userRequestDto, DeliveryAgentDetails.class);
	    deliveryAgent.setUser(savedUser);

	   
	    DeliveryAgentDetails savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);

	    return modelMapper.map(savedUser, UserResponseDto.class);
	}


	@Override
	public UserResponseDto updateUser(int userId, UserRequestDto userRequestDto) {
		Optional<User> userOptional = userRepository.findById(userId);
		if (!userOptional.isPresent()) {
			throw new RuntimeException("User not found with ID: " + userId);
		}

		User user = userOptional.get();

		if (userRequestDto.getUserFirstName() != null) {
			user.setUserFirstName(userRequestDto.getUserFirstName());
		}
		if (userRequestDto.getUserLastName() != null) {
			user.setUserLastName(userRequestDto.getUserLastName());
		}
		if (userRequestDto.getPassword() != null) {
			user.setUserPassword(userRequestDto.getPassword()); 
		}

		Optional<Role> roleOptional = roleRepository.findById(RoleType.USER);
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Role USER not found");
		}
		user.setRole(roleOptional.get());

		User updatedUser = userRepository.save(user);

		return modelMapper.map(updatedUser, UserResponseDto.class);
	}

	@Override
	public UserResponseDto verifyUserCredentials(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));

		if (!user.getUserPassword().equals(password)) {
			throw new RuntimeException("Invalid password");
		}

		return modelMapper.map(user, UserResponseDto.class);
	}
	
	@Override
	public Page<UserResponseDto> getAllUsersWithRoleUser(int page, int size) {
	    Optional<Role> roleOptional = roleRepository.findById(RoleType.USER);
	    if (!roleOptional.isPresent()) {
	        throw new RuntimeException("Role USER not found");
	    }
	    Role userRole = roleOptional.get();
	    Pageable pageable = PageRequest.of(page, size);
	    Page<User> userPage = userRepository.findAllByRole(userRole, pageable);
	  
	    return userPage.map(user -> modelMapper.map(user, UserResponseDto.class));
	}
	
	@Override
	public Page<DeliveryAgentResponseDto> getAllDeliveryAgents(int page, int size) {
	    
	    Optional<Role> roleOptional = roleRepository.findById(RoleType.DELIVERY_AGENT);
	    if (!roleOptional.isPresent()) {
	        throw new RuntimeException("Role DELIVERY_AGENT not found");
	    }
	    Role deliveryAgentRole = roleOptional.get();

	    Pageable pageable = PageRequest.of(page, size);

	    Page<User> deliveryAgentsPage = userRepository.findAllByRole(deliveryAgentRole, pageable);
   
	    return deliveryAgentsPage.map(user -> {	        
	        DeliveryAgentResponseDto responseDto = modelMapper.map(user, DeliveryAgentResponseDto.class);	        
	        DeliveryAgentDetails deliveryAgentDetails = user.getDeliveryAgentDetails();        
	        responseDto.setContactNumber(deliveryAgentDetails.getContactNumber());
	        responseDto.setDeliveryZone(deliveryAgentDetails.getDeliveryZone());
	        return responseDto;
	    });
	}






	
	
}
