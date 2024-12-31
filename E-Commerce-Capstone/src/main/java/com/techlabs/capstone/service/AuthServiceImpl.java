package com.techlabs.capstone.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.JwtAuthResponse;
import com.techlabs.capstone.dto.LoginDto;
import com.techlabs.capstone.dto.RegistrationDto;
import com.techlabs.capstone.dto.UserResponseDto;
import com.techlabs.capstone.entity.CustomerDetails;
import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.exception.UserApiException;
import com.techlabs.capstone.repository.CustomerDetailsRepository;
import com.techlabs.capstone.repository.RoleRepository;
import com.techlabs.capstone.repository.UserRepository;
import com.techlabs.capstone.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private CustomerDetailsRepository customerDetailsRepo;

	@Override
	public UserResponseDto register(RegistrationDto registration) {
	    // Check if user with the same email already exists
	    Optional<User> existingUser = userRepo.findByEmail(registration.getEmail());
	    if (existingUser.isPresent()) {
	        throw new UserApiException(HttpStatus.BAD_REQUEST, "User already exists.");
	    }

	    // Create a new User object
	    User user = new User();
	    user.setEmail(registration.getEmail());

	    // Encode the password before saving
	    user.setPassword(passwordEncoder.encode(registration.getPassword()));
	    user.setActive(true);

	    // Fetch the role from the role repository
	    List<Role> roles = roleRepo.findByRole("ROLE_CUSTOMER");
	    if (roles.isEmpty()) {
	        throw new UserApiException(HttpStatus.BAD_REQUEST, "Role not found.");
	    }

	    // Assign the role(s) to the user
	    user.setRoles(roles); // Assigning the roles

	    // Create customer details and associate with the user
	    CustomerDetails customerDetails = new CustomerDetails();
	    customerDetails.setFirstName(registration.getFirstname());
	    customerDetails.setLastName(registration.getLastname());
	    customerDetails.setUser(user);

	    // Save the user object into the database
	    userRepo.save(user);

	    // Save the customer details object into the database
	    customerDetailsRepo.save(customerDetails);

	    // Use ModelMapper to map User to UserResponseDto
	    UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

	    // Map firstName and lastName manually from CustomerDetails since it's a separate entity
	    userResponseDto.setFirstname(customerDetails.getFirstName());
	    userResponseDto.setLastname(customerDetails.getLastName());

	    // Set the role(s) as a comma-separated string or a single role (depending on your design)
	    userResponseDto.setRole(roles.stream()
	            .map(Role::getRole) // Get the role name (like "ROLE_CUSTOMER")
	            .collect(Collectors.joining(", "))); // Join roles if there are multiple

	    // Return the response object
	    return userResponseDto;
	}

	



	@Override
    public JwtAuthResponse login(LoginDto loginDto) {  
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);

            return new JwtAuthResponse(token);

        } catch (BadCredentialsException e) {
            throw new UserApiException(HttpStatus.BAD_REQUEST, "Invalid username or password");
        }
    }

}
