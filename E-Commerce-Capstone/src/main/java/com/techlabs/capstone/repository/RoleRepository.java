package com.techlabs.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.capstone.entity.Role;
import com.techlabs.capstone.entity.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleType> {

}
