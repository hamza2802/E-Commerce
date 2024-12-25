package com.techlabs.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techlabs.capstone.entity.ProfilePicture;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {
    
}
