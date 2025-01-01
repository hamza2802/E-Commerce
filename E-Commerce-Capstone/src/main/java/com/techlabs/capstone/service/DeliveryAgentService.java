package com.techlabs.capstone.service;

import org.springframework.data.domain.Page;

import com.techlabs.capstone.dto.DeliveryAgentDetailsRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentResponseDto;

public interface DeliveryAgentService {

	Page<DeliveryAgentResponseDto> getAllDeliveryAgents(int page, int size);

	DeliveryAgentResponseDto addDeliveryAgent(DeliveryAgentRequestDto requestDTO);

	DeliveryAgentResponseDto updateDeliveryAgent(int deliveryAgentId, DeliveryAgentDetailsRequestDto requestDTO);

}
