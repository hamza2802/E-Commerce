package com.techlabs.capstone.entity;

import jakarta.persistence.*;
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

    @Column(name = "first_name")
    private String userFirstName;

    @Column(name = "last_name")
    private String userLastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String userPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private DeliveryAgentDetails deliveryAgentDetails; 
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserDetails userDetails; 
    
    
}
