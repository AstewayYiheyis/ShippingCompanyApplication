package com.example.shippingcompanyapplication.entities;

import com.example.shippingcompanyapplication.common_classes.ShippingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ShippedPackage {
    @Id
    private Long id;
    public String productName;
    public String productDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    public Customer customer;
    public String trackingNumber;
    public double weight;
    public ShippingType shippingtype;

    public String shippedDate;

    public double price;

    public ShippedPackage(String productName, String productDescription, Customer customer, String trackingNumber, double weight, ShippingType shippingtype, String shippedDate, double price) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.customer = customer;
        this.trackingNumber = trackingNumber;
        this.weight = weight;
        this.shippingtype = shippingtype;
        this.shippedDate = shippedDate;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Long getId() {
        return id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ShippingType getShippingtype() {
        return shippingtype;
    }

    public void setShippingtype(ShippingType shippingtype) {
        this.shippingtype = shippingtype;
    }
}
