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
    
    @Column(name="contactnumber")
    private long contactNumber;

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
