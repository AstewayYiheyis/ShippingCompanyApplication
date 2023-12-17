package com.example.shippingcompanyapplication.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ShipmentInformation {
    @Id
    public long productInformationId;
    public String productName;
    public String productDescription;
    public String trackingNumber;
    public String shippedDate;
    public double price;

    public ShipmentInformation(String productName, String productDescription, String trackingNumber, String shippedDate, double price) {
        super();
        this.productName = productName;
        this.productDescription = productDescription;
        this.trackingNumber = trackingNumber;
        this.shippedDate = shippedDate;
        this.price = price;
    }

    public long getProductInformationId() {
        return productInformationId;
    }

    public void setProductInformationId(long productInformationId) {
        this.productInformationId = productInformationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}