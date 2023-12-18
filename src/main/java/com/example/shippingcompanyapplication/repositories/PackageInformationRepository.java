package com.example.shippingcompanyapplication.repositories;

import com.example.shippingcompanyapplication.entities.ShipmentInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageInformationRepository extends MongoRepository<ShipmentInformation, String> {
    ShipmentInformation save(ShipmentInformation packageInformation);
}
