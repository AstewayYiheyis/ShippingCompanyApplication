package com.example.shippingcompanyapplication.controllers;

import com.example.shippingcompanyapplication.dto.ShipmentInformationDTO;
import com.example.shippingcompanyapplication.entities.ShipmentInformation;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.services.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/shipping")
@EnableCaching
public class ShippingController {
    ShippingService shippingService;
    Logger logger = LoggerFactory.getLogger(ShippingController.class);

    @Autowired
    ShippingController(final ShippingService shippingService){
        this.shippingService = shippingService;
    }

    @PostMapping("/sendPackage")
    public ResponseEntity<ShipmentInformation> shipPackage(@RequestBody ShippedPackage shippedPackage){
        return new ResponseEntity(shippingService.shipPackage(shippedPackage), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable("id") String id)
    {
        return new ResponseEntity(shippingService.deletePackage(id), HttpStatusCode.valueOf(204));
    }

    @GetMapping
    @Cacheable(value = "shipmentInformation")
    public ResponseEntity<ShipmentInformationDTO> trackPackage(@RequestParam("trackingNumber") String trackingNumber){
        return new ResponseEntity(shippingService.trackPackage(trackingNumber), HttpStatusCode.valueOf(200));
    }
}
