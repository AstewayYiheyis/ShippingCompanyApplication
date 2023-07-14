package com.example.shippingcompanyapplication.services;

import com.example.shippingcompanyapplication.entities.PackageInformation;
import com.example.shippingcompanyapplication.entities.ShippedPackage;

public interface ShippingService {
    PackageInformation shipPackage(ShippedPackage shippedPackage);
    void sendMessage(String msg);
}
