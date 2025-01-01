package com.techlabs.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.capstone.dto.CartItemRequestDto;
import com.techlabs.capstone.dto.CartItemResponseDto;
import com.techlabs.capstone.dto.CartResponseDto;
import com.techlabs.capstone.service.CartServiceImpl;

@RestController
@RequestMapping("/e-commerce/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponseDto> getCart() {
        CartResponseDto cartResponse = cartService.getCart();
        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartItemResponseDto> addCartItemToCart(@RequestBody CartItemRequestDto cartItemRequestDto) {
        CartItemResponseDto cartItemResponse = cartService.addCartItemToCart(cartItemRequestDto);
        return ResponseEntity.ok(cartItemResponse);
    }

    @DeleteMapping("/remove/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> removeCartItemFromCart(@PathVariable int cartItemId) {
        cartService.removeCartItemFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable int cartItemId,
                                                       @RequestBody int quantity) {
     
        cartService.updateCartItemQuantity(cartItemId, quantity);
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Double> getCartTotalAmount() {
        double totalAmount = cartService.getCartTotalAmount();
        return ResponseEntity.ok(totalAmount);
    }
}
