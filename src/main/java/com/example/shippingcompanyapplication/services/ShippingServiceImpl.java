package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.EmailDetails;
import com.example.shippingcompanyapplication.entities.PackageInformation;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class ShippingServiceImpl {
    EmailService emailService;
    ShippingRepository shippingRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topicName}")
    private String topicName;

    @Autowired
    ShippingServiceImpl(final EmailService emailService, final ShippingRepository shippingRepository){
        this.emailService = emailService;
        this.shippingRepository = shippingRepository;
    }

    public PackageInformation shipPackage(ShippedPackage shippedPackage) {
        // generate tracking number make sure it is not duplicate by checking the database
        String trackingNumber = generateTrackingNumber();
        boolean isNotDuplicate = checkForDuplicateTrackingNumber(trackingNumber);

        while(!isNotDuplicate){
            trackingNumber = generateTrackingNumber();
            isNotDuplicate = checkForDuplicateTrackingNumber(trackingNumber);
        }
        shippedPackage.setTrackingNumber(trackingNumber);

        shippingRepository.save(shippedPackage);

        if(shippedPackage.customer != null){
            // need something to handle exception in case if there is an error when sending an email
            EmailDetails emailDetails = EmailDetails.builder().recipient(shippedPackage.customer.getEmail())
                    .subject("Shipping Notification")
                    .msgBody("This is a confirmation that your package with tracking number #" + trackingNumber
                            + " has been Shipped!").build();

            try{
                sendEmail(emailDetails);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        PackageInformation packageInformation = PackageInformation.builder()
                .productName(shippedPackage.productName).productDescription(shippedPackage.productDescription)
                .trackingNumber(trackingNumber).shippedDate(formatter.format(date)).build();

        return packageInformation;
    }

    public void sendEmail(EmailDetails msg) {

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, msg);

        future.whenComplete((result, ex) -> {
            if(ex == null){
                System.out.println("Sent message=[" + msg + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            else{
                System.out.println("Unable to send message=[" + msg + "] due to : " + ex.getMessage());
            }
        });
    }

    public String generateTrackingNumber(){
        StringBuilder trackingNumber = new StringBuilder("AB");

        SecureRandom secureRandom = new SecureRandom();
        int num = secureRandom.nextInt(10_000_000);
        String formattedTrackingNumber = String.format("%09d", num);

        trackingNumber.append(formattedTrackingNumber);

        return trackingNumber.toString();
    }

    public boolean checkForDuplicateTrackingNumber(String trackingNumber) {
        Integer count = shippingRepository.countNumberOfOrders(trackingNumber);

        if(count == null){
            return true;
        }

        return count == 0;
    }

    public PackageInformation trackPackage(String trackingNumber) {
        ShippedPackage shippedPackage = shippingRepository.findByTrackingNumber(trackingNumber);

        return PackageInformation.builder()
                .productName(shippedPackage.productName)
                .productDescription(shippedPackage.productDescription)
                .trackingNumber(shippedPackage.trackingNumber)
                .build();
    }
}
