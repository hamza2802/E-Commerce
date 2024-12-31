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
@Table(name = "customer_details")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CustomerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_details_id")
	private int customerDetailsId;
	
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

	@Column(name = "contact_number")
	private String contactNumber;

	@Column(name = "alternate_contact_number")
	private String alternateContactNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "State")
	private String state;

	@Column(name = "pincode")
	private int pincode;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

}
