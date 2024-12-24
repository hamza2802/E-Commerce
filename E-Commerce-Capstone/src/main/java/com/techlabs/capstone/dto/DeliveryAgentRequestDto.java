package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class DeliveryAgentRequestDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private long contactNumber;
    private String vehicleType;
    private String vehicleNumber;
    private String deliveryZone;
}
