package com.techlabs.capstone.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.CartItemRequestDto;
import com.techlabs.capstone.dto.CartItemResponseDto;
import com.techlabs.capstone.dto.CartResponseDto;
import com.techlabs.capstone.entity.Cart;
import com.techlabs.capstone.entity.CartItem;
import com.techlabs.capstone.entity.Product;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.CartItemRepository;
import com.techlabs.capstone.repository.CartRepository;
import com.techlabs.capstone.repository.ProductRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CartResponseDto getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (cartOptional.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartOptional.get();

        // Convert CartItems to CartItemResponseDto
        List<CartItemResponseDto> cartItems = cart.getCartItems().stream()
            .map(cartItem -> {
                Product product = cartItem.getProduct();
                return new CartItemResponseDto(
                	cartItem.getId(),
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDiscountedPrice(),
                    cartItem.getQuantity(),
                    cartItem.getTotalPrice()
                );
            })
            .collect(Collectors.toList());

        // Return CartResponseDto
        return new CartResponseDto(cart.getId(), cart.getTotalAmount(), cartItems);
    }

    @Override
    public CartItemResponseDto addCartItemToCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = getCartEntity();
        Optional<Product> productOptional = productRepository.findById(cartItemRequestDto.getProductId());
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOptional.get();
        double productPrice = product.getProductDiscountedPrice();
        double totalPrice = productPrice * cartItemRequestDto.getQuantity();

        CartItem cartItem = new CartItem(cart, product, cartItemRequestDto.getQuantity(), productPrice, totalPrice);
        cart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        // Return the CartItemResponseDto
        return new CartItemResponseDto(
        		cartItem.getId(),
            product.getProductId(),
            product.getProductName(),
            product.getProductDiscountedPrice(),
            cartItem.getQuantity(),
            cartItem.getTotalPrice()
        );
    }

    @Override
    public void removeCartItemFromCart(int cartItemId) {
        Cart cart = getCartEntity();
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cart.removeCartItem(cartItem);

            cartItemRepository.delete(cartItem);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    @Override
    public void updateCartItemQuantity(int cartItemId, int quantity) {
     
        Cart cart = getCartEntity();

        // Find the CartItem by its ID
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        // If the CartItem is found, update its quantity and total price
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            
            // Set the new quantity
            cartItem.setQuantity(quantity);
            
            // Recalculate the total price based on the updated quantity
            double updatedTotalPrice = cartItem.getPrice() * quantity;
            cartItem.setTotalPrice(updatedTotalPrice);

            // Recalculate the total amount for the entire cart after the update
            cart.recalculateTotalAmount();

            // Save the updated CartItem and Cart back to the database
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }


    @Override
    public double getCartTotalAmount() {
        Cart cart = getCartEntity();
        cart.recalculateTotalAmount();
        return cart.getTotalAmount();
    }

    private Cart getCartEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (cartOptional.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }
        return cartOptional.get();
    }

	
}
