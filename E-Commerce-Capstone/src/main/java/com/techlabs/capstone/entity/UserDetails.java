package com.techlabs.capstone.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user_details")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_details_id")
	private int userDetailsId;

	@Column(name = "contact_number")
	private long contactNumber;

	@Column(name = "alternate_contact_number")
	private long alternateContactNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "State")
	private String State;

	@Column(name = "pincode")
	private int pincode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

}
