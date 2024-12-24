//package com.techlabs.capstone.service;
//
//import java.util.Optional;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Service;
//
//import com.techlabs.capstone.dto.UserRequestDto;
//import com.techlabs.capstone.dto.UserResponseDto;
//import com.techlabs.capstone.entity.DeliveryAgentDetails;
//import com.techlabs.capstone.entity.Role;
//import com.techlabs.capstone.entity.User;
//import com.techlabs.capstone.repository.DeliveryAgentDetailsRepository;
//import com.techlabs.capstone.repository.RoleRepository;
//import com.techlabs.capstone.repository.UserRepository;
//
//@Service
//@DependsOn("roleServiceImpl")  
//public class UserServiceImpl implements UserService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private DeliveryAgentDetailsRepository deliveryAgentRepository;
//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private ModelMapper modelMapper;
//
//	

//	@Override
//	public UserResponseDto addNewDeliveryAgent(UserRequestDto userRequestDto) {
//	    // Map Role to User entity
//	    Optional<Role> roleOptional = roleRepository.findById(RoleType.DELIVERY_AGENT);
//	    if (!roleOptional.isPresent()) {
//	        throw new RuntimeException("Role DELIVERY_AGENT not found");
//	    }
//
//	    // Use ModelMapper to convert UserRequestDto to User entity
//	    User user = modelMapper.map(userRequestDto, User.class);
//	    user.setRole(roleOptional.get());
//
//	    // Save User
//	    User savedUser = userRepository.save(user);
//
//	    // Create DeliveryAgentDetails and use ModelMapper to map from UserRequestDto
//	    DeliveryAgentDetails deliveryAgent = modelMapper.map(userRequestDto, DeliveryAgentDetails.class);
//	    deliveryAgent.setUser(savedUser);
//
//	    // Save DeliveryAgentDetails
//	    DeliveryAgentDetails savedDeliveryAgent = deliveryAgentRepository.save(deliveryAgent);
//
//	    // Map the saved User entity to UserResponseDto
//	    return modelMapper.map(savedUser, UserResponseDto.class);
//	}


//	@Override
//	public UserResponseDto updateUser(int userId, UserRequestDto userRequestDto) {
//		Optional<User> userOptional = userRepository.findById(userId);
//		if (!userOptional.isPresent()) {
//			throw new RuntimeException("User not found with ID: " + userId);
//		}
//
//		User user = userOptional.get();
//
//		if (userRequestDto.getUserFirstName() != null) {
//			user.setUserFirstName(userRequestDto.getUserFirstName());
//		}
//		if (userRequestDto.getUserLastName() != null) {
//			user.setUserLastName(userRequestDto.getUserLastName());
//		}
//		if (userRequestDto.getPassword() != null) {
//			user.setUserPassword(userRequestDto.getPassword()); 
//		}
//
//		Optional<Role> roleOptional = roleRepository.findById(RoleType.USER);
//		if (!roleOptional.isPresent()) {
//			throw new RuntimeException("Role USER not found");
//		}
//		user.setRole(roleOptional.get());
//
//		User updatedUser = userRepository.save(user);
//
//		return modelMapper.map(updatedUser, UserResponseDto.class);
//	}
//
//	@Override
//	public UserResponseDto verifyUserCredentials(String email, String password) {
//		User user = userRepository.findByEmail(email)
//				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//
//		if (!user.getUserPassword().equals(password)) {
//			throw new RuntimeException("Invalid password");
//		}
//
//		return modelMapper.map(user, UserResponseDto.class);
//	}
//	
//	@Override
//	public Page<UserResponseDto> getAllUsersWithRoleUser(int page, int size) {
//	    Optional<Role> roleOptional = roleRepository.findById(RoleType.USER);
//	    if (!roleOptional.isPresent()) {
//	        throw new RuntimeException("Role USER not found");
//	    }
//
//	    Role userRole = roleOptional.get();
//	    Pageable pageable = PageRequest.of(page, size);
//
//	    Page<User> userPage = userRepository.findAllByRole(userRole, pageable);
//
//	  
//	    return userPage.map(user -> modelMapper.map(user, UserResponseDto.class));
//	}
//

//	
//	
//}
