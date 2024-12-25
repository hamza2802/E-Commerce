package com.techlabs.capstone.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ProfilePictureService {
    

	void uploadProfilePicture(MultipartFile file) throws IOException;
}
