package com.techlabs.capstone.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techlabs.capstone.entity.ProfilePicture;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.ProfilePictureRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class ProfilePictureServiceImpl implements ProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public void uploadProfilePicture(MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        String imageUrl = (String) uploadResult.get("url");

        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setImageUrl(imageUrl);
        profilePicture.setUser(user);

        profilePictureRepository.save(profilePicture);
    }
}
