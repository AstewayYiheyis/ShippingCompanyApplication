package com.example.shippingcompanyapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @KafkaListener(topics = "email_topic", groupId = "group_one")
    public void sendEmail(String email) {
        System.out.println("Email received: " + email);
    }
}
