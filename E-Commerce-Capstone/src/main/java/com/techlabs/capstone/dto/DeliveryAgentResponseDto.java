package com.techlabs.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryAgentResponseDto {
    private String userFirstName;
    private String userLastName;
    private String email;
    private long contactNumber;
    private String deliveryZone;
}
