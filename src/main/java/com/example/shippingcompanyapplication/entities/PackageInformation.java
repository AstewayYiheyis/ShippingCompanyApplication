package com.example.shippingcompanyapplication.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PackageInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long productInformationId;
    public String productName;
    public String productDescription;
    public String trackingNumber;
    public String shippedDate;
}
