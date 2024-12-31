package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.DeliveryAgentRequestDto;
import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.service.DeliveryAgentService;

@RestController
@RequestMapping("/e-commerce/delivery-agents")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryAgentController {

    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeliveryAgentResponseDto> addDeliveryAgent(@RequestBody DeliveryAgentRequestDto requestDTO) {
        DeliveryAgentResponseDto responseDTO = deliveryAgentService.addDeliveryAgent(requestDTO);

        // Return the response wrapped in ResponseEntity with HTTP status CREATED
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<DeliveryAgentResponseDto>> getAllDeliveryAgents(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<DeliveryAgentResponseDto> deliveryAgents = deliveryAgentService.getAllDeliveryAgents(page, size);

        return ResponseEntity.ok(deliveryAgents);
    }
}
