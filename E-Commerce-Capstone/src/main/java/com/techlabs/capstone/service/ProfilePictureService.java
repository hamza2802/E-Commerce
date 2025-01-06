package com.techlabs.capstone.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.techlabs.capstone.dto.ProfilePictureResponseDto;
import com.techlabs.capstone.entity.ProfilePicture;

public interface ProfilePictureService {
	
	void uploadProfilePicture(MultipartFile file) throws IOException;

	ProfilePictureResponseDto getProfilePictureByToken();
}
