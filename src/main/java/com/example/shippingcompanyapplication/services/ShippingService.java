package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.PricingClient;
import com.example.shippingcompanyapplication.PackageDetail;
import com.example.shippingcompanyapplication.consumers.NotificationService;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.entities.ShipmentInformation;
import com.example.shippingcompanyapplication.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShippingService {
    NotificationService notificationService;
    ShippingRepository shippingRepository;

    PricingClient pricingClient;
    @Autowired
    ShippingService(final NotificationService notificationService, final EmailService emailService, final ShippingRepository shippingRepository, PricingClient pricingClient){
        this.notificationService = notificationService;
        this.shippingRepository = shippingRepository;
        this.pricingClient = pricingClient;
    }

    public ShipmentInformation shipPackage(ShippedPackage shippedPackage) {
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
            try{
                notificationService.sendNotification(shippedPackage.customer.email);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        PackageDetail packageDetail = PackageDetail.builder().weight(shippedPackage.getWeight())
                .shippingtype(shippedPackage.getShippingtype()).build();

        // get pricing from pricing service
        double price = pricingClient.getPricing(packageDetail);

        ShipmentInformation shippmentInformation = new ShipmentInformation(
                shippedPackage.productName,
                shippedPackage.productDescription, trackingNumber, formatter.format(date), price);

        return shippmentInformation;
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

    public ShipmentInformation trackPackage(String trackingNumber) {
        ShippedPackage shippedPackage = shippingRepository.findByTrackingNumber(trackingNumber);

        ShipmentInformation shippmentInformation = new ShipmentInformation(shippedPackage.getProductName(),
                shippedPackage.getProductDescription(), shippedPackage.getTrackingNumber(), shippedPackage.getShippedDate(), shippedPackage.getPrice());

        return shippmentInformation;
    }
}
