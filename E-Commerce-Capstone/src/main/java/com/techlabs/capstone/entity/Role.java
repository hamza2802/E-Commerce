package com.techlabs.capstone.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private RoleType role;

	@Column(name = "description")
	private String description;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<User> users;

}
