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
@Table(name = "admin_details")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class AdminDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_details_id")
	private int adminsDetailsId;
	
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", unique = true)
	private User user;

}
