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

import com.techlabs.capstone.dto.CustomerDetailsResponseDto;
import com.techlabs.capstone.dto.DeliveryAgentResponseDto;
import com.techlabs.capstone.dto.OrderItemResponseDto;
import com.techlabs.capstone.dto.OrderResponseDto;
import com.techlabs.capstone.entity.Cart;
import com.techlabs.capstone.entity.DeliveryAgentDetails;
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

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(new java.util.Date());
        order.setDeliveryAgent(null);

        orderRepository.save(order);

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

        orderItemRepository.saveAll(orderItems);

        cart.clearCart();
        cartRepository.save(cart);

        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        List<OrderItemResponseDto> orderItemDtos = orderItems.stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                .collect(Collectors.toList());

        orderResponseDto.setOrderItems(orderItemDtos);

        if (order.getDeliveryAgent() != null) {
            orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
        } else {
            orderResponseDto.setDeliveryAgent(null);
        }

        if (user.getCustomerDetails() != null) {
            CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(user.getCustomerDetails(), CustomerDetailsResponseDto.class);
            customerDetailsResponseDto.setEmail(user.getEmail());
            orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
        } else {
            orderResponseDto.setCustomerDetails(null);
        }

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllPlacedOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.PLACED, pageable);

        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);
                    }

                    if (order.getUser().getCustomerDetails() != null) {
                        CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
                        customerDetailsResponseDto.setEmail(order.getUser().getEmail());
                        orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
                    } else {
                        orderResponseDto.setCustomerDetails(null);
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> getAllOutForDeliveryOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.OUT_FOR_DELIVERY, pageable);

        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);
                    }

                    if (order.getUser().getCustomerDetails() != null) {
                        CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
                        customerDetailsResponseDto.setEmail(order.getUser().getEmail());
                        orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
                    } else {
                        orderResponseDto.setCustomerDetails(null);
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> getAllDeliveredOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.DELIVERED, pageable);

        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);
                    }

                    if (order.getUser().getCustomerDetails() != null) {
                        CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
                        customerDetailsResponseDto.setEmail(order.getUser().getEmail());
                        orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
                    } else {
                        orderResponseDto.setCustomerDetails(null);
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> ordersPage = orderRepository.findByUser(user, pageable);

        List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream()
                .map(order -> {
                    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
                    List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                            .map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
                            .collect(Collectors.toList());

                    orderResponseDto.setOrderItems(orderItemDtos);

                    if (order.getDeliveryAgent() != null) {
                        orderResponseDto.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
                    } else {
                        orderResponseDto.setDeliveryAgent(null);
                    }

                    if (order.getUser().getCustomerDetails() != null) {
                        CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
                        customerDetailsResponseDto.setEmail(order.getUser().getEmail());
                        orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
                    } else {
                        orderResponseDto.setCustomerDetails(null);
                    }

                    return orderResponseDto;
                })
                .collect(Collectors.toList());

        return orderResponseDtos;
    }
    

    @Override
    public List<DeliveryAgentResponseDto> getDeliveryAgentsByOrderId(int orderId) {
        // Step 1: Fetch the order by orderId
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        Order order = orderOptional.get();

        // Step 2: Get the customer's city (location)
        String customerCity = order.getUser().getCustomerDetails().getCity(); // Adjust as needed

        // Step 3: Fetch all delivery agents whose deliveryZone matches the customer's city
        List<DeliveryAgentDetails> deliveryAgentDetailsList = deliveryAgentDetailsRepository
                .findByDeliveryZoneIgnoreCase(customerCity);

        // Step 4: Map the list of DeliveryAgentDetails to DeliveryAgentResponseDto
        List<DeliveryAgentResponseDto> deliveryAgentResponseDtos = deliveryAgentDetailsList.stream()
                .map(deliveryAgentDetails -> modelMapper.map(deliveryAgentDetails, DeliveryAgentResponseDto.class))
                .collect(Collectors.toList());

        // Step 5: Return the list of matching delivery agents
        return deliveryAgentResponseDtos;
    }

    @Override
    public OrderResponseDto getOrderById(int orderId) {
        return null;
    }
}
