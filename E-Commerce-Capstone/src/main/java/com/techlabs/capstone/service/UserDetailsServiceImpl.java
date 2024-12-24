//package com.techlabs.capstone.service;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.techlabs.capstone.dto.UserDetailsRequestDto;
//import com.techlabs.capstone.dto.UserDetailsResponseDto;
//import com.techlabs.capstone.entity.User;
//import com.techlabs.capstone.entity.UserDetails;
//import com.techlabs.capstone.repository.UserDetailsRepository;
//import com.techlabs.capstone.repository.UserRepository;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserDetailsRepository userDetailsRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public UserDetailsResponseDto saveOrUpdateUserDetails(int userId, UserDetailsRequestDto userDetailsRequestDto) {
//
//        // Fetch the user from the database using the userId from the URL path
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
//
//        // Try to find existing user details using the userId, or create a new one if not found
//        UserDetails userDetails = userDetailsRepository.findByUser_UserId(userId)
//                .orElse(new UserDetails());
//
//        // Set the fields from the request DTO to the user details entity
//        userDetails.setContactNumber(userDetailsRequestDto.getContactNumber());
//        userDetails.setAlternateContactNumber(userDetailsRequestDto.getAlternateContactNumber());
//        userDetails.setAddress(userDetailsRequestDto.getAddress());
//        userDetails.setCity(userDetailsRequestDto.getCity());
//        userDetails.setState(userDetailsRequestDto.getState());
//        userDetails.setPincode(userDetailsRequestDto.getPincode());
//
//        // Associate the user with the userDetails
//        userDetails.setUser(user);
//
//        // Save or update the user details
//        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);
//
//        // Map the saved entity to the response DTO
//        UserDetailsResponseDto responseDto = modelMapper.map(savedUserDetails, UserDetailsResponseDto.class);
//
//        // Set the userId in the response DTO (it should be included for response)
//        responseDto.setUserId(user.getUserId()); 
//
//        return responseDto;
//    }
//
//}
