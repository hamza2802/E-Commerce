package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.JwtAuthResponse;
import com.techlabs.capstone.dto.LoginDto;
import com.techlabs.capstone.dto.RegistrationDto;
import com.techlabs.capstone.dto.UserResponseDto;
import com.techlabs.capstone.service.AuthService;

@RestController
@RequestMapping("/e-commerce") 
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody RegistrationDto registrationDto) {

		return new ResponseEntity<>(authService.register(registrationDto), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		System.out.println("hi");
		return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
	}

}
