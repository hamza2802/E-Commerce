package com.techlabs.capstone.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.OrderItemResponseDto;
import com.techlabs.capstone.dto.OrderResponseDto;
import com.techlabs.capstone.entity.Cart;
import com.techlabs.capstone.entity.Order;
import com.techlabs.capstone.entity.OrderItem;
import com.techlabs.capstone.entity.OrderStatus;
import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.CartRepository;
import com.techlabs.capstone.repository.DeliveryAgentDetailsRepository;
import com.techlabs.capstone.repository.OrderItemRepository;
import com.techlabs.capstone.repository.OrderRepository;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;
    

    @Autowired
    private DeliveryAgentDetailsRepository deliveryAgentDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder() {
        // Get the current user from the authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        // Find the user's cart
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (cartOptional.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }
        Cart cart = cartOptional.get();

        // Create the order from the cart
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(new java.util.Date());

        order.setDeliveryAgent(null);  // You want to keep this null for now

        // Save the order to the database
        orderRepository.save(order);

        // Create order items from the cart items
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setTotalPrice(cartItem.getTotalPrice());
                    return orderItem;
                })
                .collect(Collectors.toList());

        // Save the order items
        orderItemRepository.saveAll(orderItems);

        // Clear the cart after order is created (clear items, don't delete the cart itself)
        cart.clearCart();  // Removes all cart items but keeps the cart entity
        cartRepository.save(cart);  // Save updated cart with no items

        // Map the order and order items to DTO for response
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        List<OrderItemResponseDto> orderItemDtos = orderItems.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                .collect(Collectors.toList());

        orderResponseDto.setOrderItems(orderItemDtos);

        // Handle the null deliveryAgent case
        if (order.getDeliveryAgent() != null) {
            orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
        } else {
            orderResponseDto.setDeliveryAgent(null);  // Explicitly set to null if no agent is assigned
        }

        return orderResponseDto;
    }


    @Override
    public List<OrderResponseDto> getAllPlacedOrders(int page, int size) {
        // Set up pagination
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve the orders with "PLACED" status
        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.PLACED, pageable);

        // Map the orders and order items to DTOs
        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    // Handle the null deliveryAgent case
                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);  // Explicitly set null if no agent
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }
    
    @Override
    public List<OrderResponseDto> getAllOutForDeliveryOrders(int page, int size) {
        // Set up pagination
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve the orders with "SHIPPED" status
        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.OUT_FOR_DELIVERY, pageable);

        // Map the orders and order items to DTOs
        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    // Handle the null deliveryAgent case
                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);  // Explicitly set null if no agent
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }
    
    @Override
    public List<OrderResponseDto> getAllDeliveredOrders(int page, int size) {
        // Set up pagination
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve the orders with "SHIPPED" status
        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.DELIVERED, pageable);

        // Map the orders and order items to DTOs
        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    // Handle the null deliveryAgent case
                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);  // Explicitly set null if no agent
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }




    @Override
    public List<OrderResponseDto> getOrdersByUser(int page, int size) {
        // Get the current user from the authentication context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        // Set up pagination
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve the orders for the logged-in user
        Page<Order> ordersPage = orderRepository.findByUser(user, pageable);

        // Map the orders and order items to DTOs
        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);
                    orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }


	@Override
	public OrderResponseDto getOrderById(int orderId) {
		// TODO Auto-generated method stub
		return null;
	}
}
