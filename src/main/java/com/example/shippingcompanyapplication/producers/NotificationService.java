package com.example.shippingcompanyapplication.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(String email)
    {
        kafkaTemplate.send("shipping_email_topic", email);
    }
}
