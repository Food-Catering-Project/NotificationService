package com.example.NotificationService.Listener;

import com.example.NotificationService.Dto.CateringOrder;
import com.example.NotificationService.Dto.OrderEvent;
import com.example.NotificationService.Service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CateringOrderListener {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    public CateringOrderListener(MailService mailService, ObjectMapper objectMapper) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "CateringorderQueue")
    public void CateringOrderEvent(String message) {
        try {
           CateringOrder cateringOrder = objectMapper.readValue(message, CateringOrder.class);

           System.out.println(cateringOrder.getEmail());

            // Prepare variables for CateringOrderEvent email
            Map<String, Object> userVariables = new HashMap<>();
            userVariables.put("Email", cateringOrder.getEmail());
            System.out.println("inside uservariable" +cateringOrder.getEmail());

            userVariables.put("noOfPerson", cateringOrder.getNoOfPerson());
            userVariables.put("CateringorderId", cateringOrder.getCateringOrderid());
            userVariables.put("phoneNo", cateringOrder.getPhoneNo());
            userVariables.put("Date", cateringOrder.getDate());



            // Send emails
            mailService.sendOrderEmail(cateringOrder.getEmail(), "CateringOrder Confirmation", "CateringOrder", userVariables);
            System.out.println("mail send");

        } catch (Exception e) {
            System.err.println("Error processing order event: " + e.getMessage());
        }
    }
}





