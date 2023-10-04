package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.PricingClient;
import com.example.shippingcompanyapplication.entities.PackageDetail;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.entities.ShippmentInformation;
import com.example.shippingcompanyapplication.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShippingService {
    EmailService emailService;
    ShippingRepository shippingRepository;

    PricingClient pricingClient;
    @Autowired
    ShippingService(final EmailService emailService, final ShippingRepository shippingRepository, PricingClient pricingClient){
        this.emailService = emailService;
        this.shippingRepository = shippingRepository;
        this.pricingClient = pricingClient;
    }

    public ShippmentInformation shipPackage(ShippedPackage shippedPackage) {
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
                emailService.sendEmail(shippedPackage.customer.email);
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

        ShippmentInformation packageInformation = ShippmentInformation.builder()
                .productName(shippedPackage.productName).productDescription(shippedPackage.productDescription)
                .trackingNumber(trackingNumber).shippedDate(formatter.format(date)).price(price).build();

        return packageInformation;
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

    public ShippmentInformation trackPackage(String trackingNumber) {
        ShippedPackage shippedPackage = shippingRepository.findByTrackingNumber(trackingNumber);

        return ShippmentInformation.builder()
                .productName(shippedPackage.productName)
                .productDescription(shippedPackage.productDescription)
                .trackingNumber(shippedPackage.trackingNumber)
                .build();
    }
}
