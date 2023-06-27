package com.example.shippingcompanyapplication.repositories;

import com.example.shippingcompanyapplication.entities.ShippedPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<ShippedPackage, String> {
    public ShippedPackage save(ShippedPackage shippedPackage);

    @Query("select count(trackingNumber) from ShippedPackage group by trackingNumber having trackingNumber = ?1")
    public Integer countNumberOfOrders(String trackingNumber);

    public ShippedPackage findByTrackingNumber(String trackingNumber);
}
