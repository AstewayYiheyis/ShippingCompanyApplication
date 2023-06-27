package com.example.shippingcompanyapplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShippedPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String productName;
    public String productDescription;
    @ManyToOne
    public Customer customer;
    public String trackingNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
