package com.example.NotificationService.Listener;

import com.example.NotificationService.Dto.OrderEvent;
import com.example.NotificationService.Service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderListener {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    public OrderListener(MailService mailService, ObjectMapper objectMapper) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

//    @RabbitListener(queues = "orderQueue")
//    public void consumeOrderEvent(String message) {
//        try {
//            // Convert JSON string back to OrderEvent object
//            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
//
//            String userEmailMessage = "Hello, your order #" + event.getOrderId() + " has been placed successfully.";
//            String restaurantEmailMessage = "New order received! Order #" + event.getOrderId();
//
//            mailService.sendEmail(event.getUserEmail(), "Order Confirmation", userEmailMessage);
//            mailService.sendEmail(event.getRestaurantEmail(), "New Order Received", restaurantEmailMessage);
//        } catch (Exception e) {
//            System.err.println("Error processing order event: " + e.getMessage());
//        }
//    }



        @RabbitListener(queues = "orderQueue")
        public void consumeOrderEvent(String message) {
            try {
                OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

                // Prepare variables for user email
                Map<String, Object> userVariables = new HashMap<>();
                userVariables.put("userName", event.getUserEmail());
                userVariables.put("restaurantName", event.getRestaurantEmail());
                userVariables.put("orderId", event.getOrderId());
                userVariables.put("orderDate", LocalDateTime.now());
                userVariables.put("totalAmount", event.getTotalAmount());

                // Prepare variables for restaurant email
                Map<String, Object> restaurantVariables = new HashMap<>();
                restaurantVariables.put("restaurantName", event.getRestaurantEmail());
                restaurantVariables.put("userName", event.getUserEmail());
                restaurantVariables.put("orderId", event.getOrderId());
                restaurantVariables.put("orderDate", LocalDateTime.now());
                restaurantVariables.put("totalAmount", event.getTotalAmount());
                restaurantVariables.put("deliveryAddress", event.getUserAddress());

                // Send emails
                mailService.sendOrderEmail(event.getUserEmail(), "Order Confirmation", "order-confirmation", userVariables);
                mailService.sendOrderEmail(event.getRestaurantEmail(), "New Order Received", "new-order-notification", restaurantVariables);

            } catch (Exception e) {
                System.err.println("Error processing order event: " + e.getMessage());
            }
        }
    }

