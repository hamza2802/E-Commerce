	package com.techlabs.capstone.service;
	
	import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
	
		@Autowired
		private EmailService emailService;
	
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
	
			List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrder(order);
				orderItem.setProduct(cartItem.getProduct());
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setTotalPrice(cartItem.getTotalPrice());
				return orderItem;
			}).collect(Collectors.toList());
	
			orderItemRepository.saveAll(orderItems);
	
			cart.clearCart();
			cartRepository.save(cart);
	
			OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
			List<OrderItemResponseDto> orderItemDtos = orderItems.stream()
					.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).collect(Collectors.toList());
	
			orderResponseDto.setOrderItems(orderItemDtos);
	
			if (order.getDeliveryAgent() != null) {
				orderResponseDto
						.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
			} else {
				orderResponseDto.setDeliveryAgent(null);
			}
	
			if (user.getCustomerDetails() != null) {
				CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper.map(user.getCustomerDetails(),
						CustomerDetailsResponseDto.class);
				customerDetailsResponseDto.setEmail(user.getEmail());
				orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
			} else {
				orderResponseDto.setCustomerDetails(null);
			}
	
			String subject = "Order Placed Successfully";
			String body = "Dear " + user.getCustomerDetails().getFirstName() + " " + user.getCustomerDetails().getLastName()
				    + ",\n\n" + "Your order has been successfully placed. Order ID: " + order.getOrderId() + ".\n\n"
				    + "Total Amount: " + order.getTotalAmount() + "\n\n"
				    + "Thank you for shopping with us!\n\n"
				    + "Best regards,\nGadgetCart Team";

			emailService.sendEmail(user.getEmail(), subject, body);
	
			return orderResponseDto;
		}
	
		@Override
		public List<OrderResponseDto> getAllPlacedOrders(int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
	
			Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.PLACED, pageable);
	
			List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream().map(order -> {
				OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
				List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
						.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
						.collect(Collectors.toList());
	
				orderResponseDto.setOrderItems(orderItemDtos);
	
				if (order.getDeliveryAgent() != null) {
					orderResponseDto
							.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
				} else {
					orderResponseDto.setDeliveryAgent(null);
				}
	
				if (order.getUser().getCustomerDetails() != null) {
					CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
							.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
					customerDetailsResponseDto.setEmail(order.getUser().getEmail());
					orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
				} else {
					orderResponseDto.setCustomerDetails(null);
				}
	
				return orderResponseDto;
			}).collect(Collectors.toList());
	
			return orderResponseDtos;
		}
	
		@Override
		public List<OrderResponseDto> getAllOutForDeliveryOrders(int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
	
			Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.OUT_FOR_DELIVERY, pageable);
	
			List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream().map(order -> {
				OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
				List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
						.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
						.collect(Collectors.toList());
	
				orderResponseDto.setOrderItems(orderItemDtos);
	
				if (order.getDeliveryAgent() != null) {
					orderResponseDto
							.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
				} else {
					orderResponseDto.setDeliveryAgent(null);
				}
	
				if (order.getUser().getCustomerDetails() != null) {
					CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
							.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
					customerDetailsResponseDto.setEmail(order.getUser().getEmail());
					orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
				} else {
					orderResponseDto.setCustomerDetails(null);
				}
	
				return orderResponseDto;
			}).collect(Collectors.toList());
	
			return orderResponseDtos;
		}
	
		@Override
		public List<OrderResponseDto> getAllDeliveredOrders(int page, int size) {
			Pageable pageable = PageRequest.of(page, size);
	
			Page<Order> ordersPage = orderRepository.findByStatus(OrderStatus.DELIVERED, pageable);
	
			List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream().map(order -> {
				OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
				List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
						.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
						.collect(Collectors.toList());
	
				orderResponseDto.setOrderItems(orderItemDtos);
	
				if (order.getDeliveryAgent() != null) {
					orderResponseDto
							.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
				} else {
					orderResponseDto.setDeliveryAgent(null);
				}
	
				if (order.getUser().getCustomerDetails() != null) {
					CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
							.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
					customerDetailsResponseDto.setEmail(order.getUser().getEmail());
					orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
				} else {
					orderResponseDto.setCustomerDetails(null);
				}
	
				return orderResponseDto;
			}).collect(Collectors.toList());
	
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
	
			List<OrderResponseDto> orderResponseDtos = ordersPage.getContent().stream().map(order -> {
				OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
				List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
						.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class))
						.collect(Collectors.toList());
	
				orderResponseDto.setOrderItems(orderItemDtos);
	
				if (order.getDeliveryAgent() != null) {
					orderResponseDto
							.setDeliveryAgent(modelMapper.map(order.getDeliveryAgent(), DeliveryAgentResponseDto.class));
				} else {
					orderResponseDto.setDeliveryAgent(null);
				}
	
				if (order.getUser().getCustomerDetails() != null) {
					CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
							.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
					customerDetailsResponseDto.setEmail(order.getUser().getEmail());
					orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
				} else {
					orderResponseDto.setCustomerDetails(null);
				}
	
				return orderResponseDto;
			}).collect(Collectors.toList());
	
			return orderResponseDtos;
		}
	
		@Override
		public List<DeliveryAgentResponseDto> getDeliveryAgentsByOrderLocation(int orderId) {
			Optional<Order> orderOptional = orderRepository.findById(orderId);
			if (orderOptional.isEmpty()) {
				throw new RuntimeException("Order not found");
			}
	
			Order order = orderOptional.get();
			String customerCity = order.getUser().getCustomerDetails().getCity();
	
			List<DeliveryAgentDetails> deliveryAgentDetailsList = deliveryAgentDetailsRepository
					.findByDeliveryZoneIgnoreCase(customerCity);
	
			List<DeliveryAgentResponseDto> deliveryAgentResponseDtos = deliveryAgentDetailsList.stream()
					.map(deliveryAgentDetails -> {
						DeliveryAgentResponseDto dto = modelMapper.map(deliveryAgentDetails,
								DeliveryAgentResponseDto.class);
	
						String deliveryAgentEmail = deliveryAgentDetails.getUser().getEmail();
	
						dto.setEmail(deliveryAgentEmail);
	
						return dto;
					}).collect(Collectors.toList());
	
			return deliveryAgentResponseDtos;
		}
	
		@Override
		public OrderResponseDto getOrderById(int orderId) {
			Optional<Order> orderOptional = orderRepository.findById(orderId);
			if (orderOptional.isEmpty()) {
				throw new RuntimeException("Order not found");
			}
			Order order = orderOptional.get();
	
			OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
	
			if (order.getDeliveryAgent() != null) {
				DeliveryAgentResponseDto deliveryAgentResponseDto = modelMapper.map(order.getDeliveryAgent(),
						DeliveryAgentResponseDto.class);
				orderResponseDto.setDeliveryAgent(deliveryAgentResponseDto);
			}
	
			if (order.getUser() != null && order.getUser().getCustomerDetails() != null) {
				CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
						.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
				orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
			}
	
			List<OrderItemResponseDto> orderItemResponseDtos = order.getOrderItems().stream().map(orderItem -> {
				OrderItemResponseDto itemDto = modelMapper.map(orderItem, OrderItemResponseDto.class);
				return itemDto;
			}).collect(Collectors.toList());
	
			orderResponseDto.setOrderItems(orderItemResponseDtos);
	
			return orderResponseDto;
		}
	
		@Override
		public OrderResponseDto assignDeliveryAgentToOrder(int orderId, int deliveryAgentId) {
			Optional<Order> orderOptional = orderRepository.findById(orderId);
			if (orderOptional.isEmpty()) {
				throw new RuntimeException("Order not found");
			}
			Order order = orderOptional.get();
	
			Optional<DeliveryAgentDetails> deliveryAgentDetailsOptional = deliveryAgentDetailsRepository
					.findById(deliveryAgentId);
			if (deliveryAgentDetailsOptional.isEmpty()) {
				throw new RuntimeException("Delivery agent not found");
			}
			DeliveryAgentDetails deliveryAgentDetails = deliveryAgentDetailsOptional.get();
	
			order.setDeliveryAgent(deliveryAgentDetails);
			order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
	
			orderRepository.save(order);
	
			OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
			DeliveryAgentResponseDto deliveryAgentResponseDto = modelMapper.map(deliveryAgentDetails,
					DeliveryAgentResponseDto.class);
	
			String deliveryAgentEmail = deliveryAgentDetails.getUser().getEmail();
			deliveryAgentResponseDto.setEmail(deliveryAgentEmail);
	
			orderResponseDto.setDeliveryAgent(deliveryAgentResponseDto);
	
			if (order.getUser().getCustomerDetails() != null) {
				CustomerDetailsResponseDto customerDetailsResponseDto = modelMapper
						.map(order.getUser().getCustomerDetails(), CustomerDetailsResponseDto.class);
				customerDetailsResponseDto.setEmail(order.getUser().getEmail());
				orderResponseDto.setCustomerDetails(customerDetailsResponseDto);
			}
	
			List<OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
					.map(orderItem -> modelMapper.map(orderItem, OrderItemResponseDto.class)).collect(Collectors.toList());
			orderResponseDto.setOrderItems(orderItemDtos);
	
			// Send email to the assigned delivery agent
			// Send email to the assigned delivery agent
			String subject = "Delivery Agent Assigned to Order #" + order.getOrderId();
			String body = "Dear " + deliveryAgentDetails.getUserFirstName() + " " + deliveryAgentDetails.getUserLastName()
			        + ",\n\n" + "You have been assigned to deliver Order #" + order.getOrderId()
			        + ". Please pick up the order and proceed with the delivery.\n\n" 
			        + "Best regards,\nGadgetCart Team";

			// Send the email to the delivery agent
			emailService.sendEmail(deliveryAgentEmail, subject, body);

			// Send email to the customer
			String customerEmail = order.getUser().getEmail();
			String customerSubject = "Your Order #" + order.getOrderId() + " is out for delivery";
			String customerBody = "Dear " + order.getUser().getCustomerDetails().getFirstName() + " "
			        + order.getUser().getCustomerDetails().getLastName() + ",\n\n" 
			        + "Your order #" + order.getOrderId() + " is now out for delivery. "
			        + "The assigned delivery agent is " + deliveryAgentDetails.getUserFirstName() + " "
			        + deliveryAgentDetails.getUserLastName() + " with Phone: +91 " 
			        + deliveryAgentDetails.getContactNumber() + ".\n\n" 
			        + "Best regards,\nGadgetCart Team";

			// Send the email to the customer
			emailService.sendEmail(customerEmail, customerSubject, customerBody);

			emailService.sendEmail(customerEmail, customerSubject, customerBody);
	
			return orderResponseDto;
		}
		
		

		@Override
		public OrderResponseDto markOrderAsDelivered(int orderId) {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String email = authentication.getName(); 
		   
		    Optional<User> userOptional = userRepository.findByEmail(email);
		    if (userOptional.isEmpty()) {
		        throw new RuntimeException("User not found");
		    }
		    User user = userOptional.get();

		    if (user.getDeliveryAgentDetails() == null) {
		        throw new RuntimeException("This user is not a delivery agent");
		    }

		    int deliveryAgentId = user.getDeliveryAgentDetails().getDeliveryAgentId();

		    Optional<Order> orderOptional = orderRepository.findById(orderId);
		    if (orderOptional.isEmpty()) {
		        throw new RuntimeException("Order not found");
		    }
		    Order order = orderOptional.get();

		    if (order.getDeliveryAgent() == null || order.getDeliveryAgent().getDeliveryAgentId() != deliveryAgentId) {
		        throw new RuntimeException("This delivery agent is not assigned to this order");
		    }

		    order.setStatus(OrderStatus.DELIVERED);

		    orderRepository.save(order);

		    OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);

		    String deliveryAgentEmail = order.getDeliveryAgent().getUser().getEmail();
		    String subjectToAgent = "Order #" + order.getOrderId() + " Delivered";
		    String bodyToAgent = "Dear " + order.getDeliveryAgent().getUserFirstName() + " " 
		            + order.getDeliveryAgent().getUserLastName() + ",\n\n" 
		            + "Congratulations! You have successfully delivered Order #" + order.getOrderId() + ".\n\n" 
		            + "Best regards,\nGadgetCart Team";
		    emailService.sendEmail(deliveryAgentEmail, subjectToAgent, bodyToAgent);

		    String customerEmail = order.getUser().getEmail();
		    String subjectToCustomer = "Your Order #" + order.getOrderId() + " Has Been Delivered";
		    String bodyToCustomer = "Dear " + order.getUser().getCustomerDetails().getFirstName() + " "
		            + order.getUser().getCustomerDetails().getLastName() + ",\n\n"
		            + "Good news! Your order #" + order.getOrderId() + " has been successfully delivered.\n\n"
		            + "Thank you for shopping with us.\n\n"
		            + "Best regards,\nGadgetCart Team";
		    emailService.sendEmail(customerEmail, subjectToCustomer, bodyToCustomer);

		    return orderResponseDto;
		}

	
	}
