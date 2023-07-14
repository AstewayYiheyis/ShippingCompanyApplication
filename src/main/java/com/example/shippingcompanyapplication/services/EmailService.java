package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${shippingapplicationtest@gmail.com}")
    private String sender;

    private final Logger logger;

    EmailService(){
        logger = Logger.getLogger(EmailService.class.getName());
    }

    @KafkaListener(topics = "shippingEmailTopic", groupId = "groupOne")
    public void sendEmail(EmailDetails emailDetails) {
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);

            logger.log(Level.INFO, "Message is Sent succesfully!");
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Error while sending email: ", e);
        }
    }
}
