package com.techlabs.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
	 
	List<Role> findByRole(String role);
}
