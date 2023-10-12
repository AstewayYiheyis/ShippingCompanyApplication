package com.example.shippingcompanyapplication.entities;

import com.example.shippingcompanyapplication.common_classes.ShippingType;
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
    public double weight;
    public ShippingType shippingtype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
