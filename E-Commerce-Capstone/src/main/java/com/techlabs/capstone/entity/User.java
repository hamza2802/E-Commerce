package com.techlabs.capstone.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    	    name = "user_roles",  // Join table name
    	    joinColumns = @JoinColumn(name = "user_id"),  // Column referring to the current entity
    	    inverseJoinColumns = @JoinColumn(name = "role_id")  // Column referring to the other entity (Role)
    	)
    	private List<Role> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private DeliveryAgentDetails deliveryAgentDetails; 
    
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private CustomerDetails customerDetails; 
    
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private AdminDetails adminDetails; 
    
    
}
