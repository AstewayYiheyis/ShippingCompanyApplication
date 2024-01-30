package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.PackageDetail;
import com.example.shippingcompanyapplication.PricingClient;
import com.example.shippingcompanyapplication.producers.NotificationService;
import com.example.shippingcompanyapplication.dto.ShipmentInformationDTO;
import com.example.shippingcompanyapplication.entities.ShipmentInformation;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.repositories.PackageInformationRepository;
import com.example.shippingcompanyapplication.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShippingService {
    NotificationService notificationService;
    ShippingRepository shippingRepository;
    PackageInformationRepository packageInformationRepository;


    PricingClient pricingClient;

    @Autowired
    ShippingService(final NotificationService notificationService, final ShippingRepository shippingRepository, final PricingClient pricingClient, final PackageInformationRepository packageInformationRepository) {
        this.notificationService = notificationService;
        this.shippingRepository = shippingRepository;
        this.pricingClient = pricingClient;
        this.packageInformationRepository = packageInformationRepository;
    }

    public ShipmentInformationDTO shipPackage(ShippedPackage shippedPackage) {
        // generate tracking number make sure it is not duplicate by checking the database
        String trackingNumber = generateTrackingNumber();
        boolean isNotDuplicate = checkForDuplicateTrackingNumber(trackingNumber);

        while (!isNotDuplicate) {
            trackingNumber = generateTrackingNumber();
            isNotDuplicate = checkForDuplicateTrackingNumber(trackingNumber);
        }
        shippedPackage.setTrackingNumber(trackingNumber);

        shippingRepository.save(shippedPackage);

        if (shippedPackage.customer != null) {
            // need something to handle exception in case if there is an error when sending an email
            try {
                notificationService.sendNotification(shippedPackage.customer.email);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        PackageDetail packageDetail = PackageDetail.builder().weight(shippedPackage.getWeight())
                .shippingtype(shippedPackage.getShippingtype()).build();

        // get pricing from pricing service
        double price = pricingClient.getPricing(packageDetail);

        ShipmentInformation shipmentInformation = new ShipmentInformation(
                shippedPackage.productName,
                shippedPackage.productDescription, trackingNumber, formatter.format(date), price);
        ShipmentInformation packageInformation = packageInformationRepository.save(shipmentInformation);

        return ShipmentInformationDTO.builder()
                .productName(packageInformation.productName)
                .productDescription(packageInformation.productDescription)
                .trackingNumber(packageInformation.getTrackingNumber())
                .shippedDate(packageInformation.getShippedDate())
                .price(packageInformation.getPrice()).build();
    }

    public String generateTrackingNumber() {
        StringBuilder trackingNumber = new StringBuilder("AB");

        SecureRandom secureRandom = new SecureRandom();
        int num = secureRandom.nextInt(10_000_000);
        String formattedTrackingNumber = String.format("%09d", num);

        trackingNumber.append(formattedTrackingNumber);

        return trackingNumber.toString();
    }

    public boolean checkForDuplicateTrackingNumber(String trackingNumber) {
        Integer count = shippingRepository.countNumberOfOrders(trackingNumber);

        if (count == null) {
            return true;
        }

        return count == 0;
    }

    public ShipmentInformationDTO trackPackage(String trackingNumber) {
        ShipmentInformation shipmentInformation = packageInformationRepository.findByTrackingNumber(trackingNumber);

        return ShipmentInformationDTO.builder()
                .productName(shipmentInformation.productName)
                .productDescription(shipmentInformation.productDescription)
                .trackingNumber(shipmentInformation.getTrackingNumber())
                .shippedDate(shipmentInformation.getShippedDate())
                .price(shipmentInformation.getPrice()).build();
    }

    public ResponseEntity<Void> deletePackage(String id) {
        shippingRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
