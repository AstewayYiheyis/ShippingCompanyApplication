package com.example.shippingcompanyapplication.dto;

import lombok.Builder;

@Builder
public class ShipmentInformationDTO {
    public String productName;
    public String productDescription;
    public String trackingNumber;
    public String shippedDate;
    public double price;
}
