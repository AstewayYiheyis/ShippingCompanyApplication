package com.example.shippingcompanyapplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "shipping_email_topic", groupId = "group_one")
    public void sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testmailforproject1234@gmail.com");
        message.setTo(email);
        message.setSubject("Package Shipped");
        message.setText("Your package has been shipped!");

        mailSender.send(message);
        System.out.println("Email received: " + email);
    }
}
