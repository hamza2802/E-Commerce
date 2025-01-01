package com.techlabs.capstone.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.DeliveryAgentDetailsRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentDetailsRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.entity.DeliveryAgentDetails;
import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.DeliveryAgentDetailsRepository;
import com.techlabs.capstone.repository.RoleRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class DeliveryAgentServiceImpl implements DeliveryAgentService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DeliveryAgentDetailsRepository deliveryAgentDetailsRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper; 

	@Override
	public DeliveryAgentResponseDto addDeliveryAgent(DeliveryAgentRequestDto requestDTO) {
		if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
			throw new RuntimeException("User with this email already exists.");
		}

		User user = new User();
		user.setEmail(requestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword())); 
		user.setActive(true);

		List<Role> roles = roleRepository.findByRole("ROLE_DELIVERY_AGENT");
		if (roles.isEmpty()) {
			throw new RuntimeException("Role 'ROLE_DELIVERY_AGENT' not found.");
		}
		user.setRoles(roles); 

		userRepository.save(user);

		DeliveryAgentDetails deliveryAgentDetails = new DeliveryAgentDetails();
		deliveryAgentDetails.setUserFirstName(requestDTO.getFirstName());
		deliveryAgentDetails.setUserLastName(requestDTO.getLastName());
		deliveryAgentDetails.setContactNumber(requestDTO.getContactNumber());
		deliveryAgentDetails.setVehicleType(requestDTO.getVehicleType());
		deliveryAgentDetails.setVehicleNumber(requestDTO.getVehicleNumber());
		deliveryAgentDetails.setDeliveryZone(requestDTO.getDeliveryZone());
		deliveryAgentDetails.setUser(user);

		deliveryAgentDetailsRepository.save(deliveryAgentDetails);

		DeliveryAgentResponseDto responseDTO = modelMapper.map(deliveryAgentDetails, DeliveryAgentResponseDto.class);
		responseDTO.setEmail(user.getEmail());

		return responseDTO;
	}

	@Override
	public Page<DeliveryAgentResponseDto> getAllDeliveryAgents(int page, int size) {
		Pageable pageableRequest = PageRequest.of(page, size);

		Page<DeliveryAgentDetails> paginatedDeliveryAgents = deliveryAgentDetailsRepository
				.findByUserIsActiveTrue(pageableRequest);

		return paginatedDeliveryAgents.map(deliveryAgentDetails -> {
			DeliveryAgentResponseDto deliveryAgentResponseDto = modelMapper.map(deliveryAgentDetails,
					DeliveryAgentResponseDto.class);
			String email = deliveryAgentDetails.getUser().getEmail();
			deliveryAgentResponseDto.setEmail(email);
			return deliveryAgentResponseDto;
		});
	}
	
	@Override
	public DeliveryAgentResponseDto updateDeliveryAgent(int deliveryAgentId, DeliveryAgentDetailsRequestDto requestDTO) {
	    
	    DeliveryAgentDetails deliveryAgentDetails = deliveryAgentDetailsRepository
	            .findById(deliveryAgentId)
	            .orElseThrow(() -> new RuntimeException("Delivery agent not found"));

	    deliveryAgentDetails.setUserFirstName(requestDTO.getFirstName());
	    deliveryAgentDetails.setUserLastName(requestDTO.getLastName());
	    deliveryAgentDetails.setContactNumber(requestDTO.getContactNumber());
	    deliveryAgentDetails.setVehicleType(requestDTO.getVehicleType());
	    deliveryAgentDetails.setVehicleNumber(requestDTO.getVehicleNumber());
	    deliveryAgentDetails.setDeliveryZone(requestDTO.getDeliveryZone());

	    deliveryAgentDetailsRepository.save(deliveryAgentDetails);

	    DeliveryAgentResponseDto responseDTO = modelMapper.map(deliveryAgentDetails, DeliveryAgentResponseDto.class);
	    responseDTO.setEmail(deliveryAgentDetails.getUser().getEmail()); // Keep original email

	    return responseDTO;
	}


}
