package com.techlabs.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.OrderResponseDto;
import com.techlabs.capstone.service.OrderService;

@RestController
@RequestMapping("/e-commerce/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDto> createOrder() {
        OrderResponseDto orderResponseDto = orderService.createOrder();
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<OrderResponseDto> orders = orderService.getOrdersByUser(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable int orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @GetMapping("/placed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllPlacedOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<OrderResponseDto> orders = orderService.getAllPlacedOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/out-for-delivery")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOutForDeliveryOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<OrderResponseDto> orders = orderService.getAllOutForDeliveryOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/delivered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllDeliveredOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<OrderResponseDto> orders = orderService.getAllDeliveredOrders(page, size);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
