package com.example.shippingcompanyapplication.controllers;

import com.example.shippingcompanyapplication.dto.ShipmentInformationDTO;
import com.example.shippingcompanyapplication.entities.ShipmentInformation;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.services.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/shipping")
public class ShippingController {
    ShippingService shippingService;

    @Autowired
    ShippingController(final ShippingService shippingService){
        this.shippingService = shippingService;
    }

    @PostMapping("/sendPackage")
    public ResponseEntity<ShipmentInformation> shipPackage(@RequestBody ShippedPackage shippedPackage){
        return new ResponseEntity(shippingService.shipPackage(shippedPackage), HttpStatusCode.valueOf(200));
    }

    @GetMapping
    public ResponseEntity<ShipmentInformationDTO> trackPackage(@RequestParam("trackingNumber") String trackingNumber){
        return new ResponseEntity(shippingService.trackPackage(trackingNumber), HttpStatusCode.valueOf(200));
    }
}
