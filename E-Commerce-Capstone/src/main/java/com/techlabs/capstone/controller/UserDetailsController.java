package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.UserDetailsRequestDto;
import com.techlabs.capstone.dto.UserDetailsResponseDto;
import com.techlabs.capstone.service.UserDetailsService;

@RestController
@RequestMapping("/e-commerce/userdetails")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/saveOrUpdate/{userId}")
    public ResponseEntity<UserDetailsResponseDto> saveOrUpdateUserDetails(
            @PathVariable("userId") int userId,
            @RequestBody UserDetailsRequestDto userDetailsRequestDto) {
        
        UserDetailsResponseDto userDetailsResponseDto = userDetailsService.saveOrUpdateUserDetails(userId, userDetailsRequestDto);

        return new ResponseEntity<>(userDetailsResponseDto, HttpStatus.OK);
    }
}
