package com.techlabs.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "delivery_agents_details")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DeliveryAgentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_agent_id")
    private int deliveryAgentId;
    
    @Column(name = "first_name")
    private String userFirstName;

    @Column(name = "last_name")
    private String userLastName;
    
    @Column(name="contactnumber")
    private String contactNumber;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "delivery_zone")
    private String deliveryZone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)  
    private User user; 
}
