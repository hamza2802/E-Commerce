package com.techlabs.capstone.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.CartItemRequestDto;
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

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Override
    public Cart getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalAmount(0.0);
        return cartRepository.save(cart);
    }

    @Override
    public CartItem addCartItemToCart(CartItemRequestDto cartItemRequestDto) {
        Cart cart = getCart();
        Optional<Product> productOptional = productRepository.findById(cartItemRequestDto.getProductId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        Product product = productOptional.get();
        double productPrice = product.getProductActualPrice();
        double totalPrice = product.getProductActualPrice() * cartItemRequestDto.getQuantity();

        CartItem cartItem = new CartItem(cart, product, cartItemRequestDto.getQuantity(), productPrice, totalPrice);

        cart.addCartItem(cartItem);

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        return cartItem;
    }

    @Override
    public void removeCartItemFromCart(int cartItemId) {
        Cart cart = getCart();
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
    public CartItem updateCartItemQuantity(int cartItemId, CartItemRequestDto cartItemRequestDto) {
        Cart cart = getCart();
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItemRequestDto.getQuantity());

            double updatedTotalPrice = cartItem.getPrice() * cartItemRequestDto.getQuantity();
            cartItem.setTotalPrice(updatedTotalPrice);

            cart.recalculateTotalAmount();

            cartItemRepository.save(cartItem);
            cartRepository.save(cart);

            return cartItem;
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    @Override
    public double getCartTotalAmount() {
        Cart cart = getCart();
        cart.recalculateTotalAmount();
        return cart.getTotalAmount();
    }
}
