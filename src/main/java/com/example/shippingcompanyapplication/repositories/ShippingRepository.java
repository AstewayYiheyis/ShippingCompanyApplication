package com.example.shippingcompanyapplication.repositories;

import com.example.shippingcompanyapplication.entities.ShippedPackage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends MongoRepository<ShippedPackage, String> {
    public ShippedPackage save(ShippedPackage shippedPackage);

    @Query("select count(trackingNumber) from ShippedPackage group by trackingNumber having trackingNumber = ?1")
    public Integer countNumberOfOrders(String trackingNumber);

    public ShippedPackage findByTrackingNumber(String trackingNumber);
}
