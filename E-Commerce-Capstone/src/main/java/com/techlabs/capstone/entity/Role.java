package com.techlabs.capstone.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Role {

	@Id
	@Column(name = "role")
	private String role;

	@Column(name = "description")
	private String description;

	@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;

}
