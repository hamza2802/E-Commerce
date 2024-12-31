package com.techlabs.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.ProfilePicture;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Integer> {
    
}
