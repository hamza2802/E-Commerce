package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techlabs.capstone.service.ProfilePictureService;

@RestController
@RequestMapping("/e-commerce/users")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfilePictureController {

	@Autowired
	private ProfilePictureService profilePictureService;

	@PostMapping("/upload-profile-picture")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('DELIVERY_AGENT')")
	public ResponseEntity<String> uploadProfilePicture(@RequestParam("file") MultipartFile file) {
	    try {
	        profilePictureService.uploadProfilePicture(file);
	        
	        return ResponseEntity.status(HttpStatus.OK).body("Profile picture has been uploaded.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile picture.");
	    }
	}

}
