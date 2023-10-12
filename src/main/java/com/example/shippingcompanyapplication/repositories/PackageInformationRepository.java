package com.example.shippingcompanyapplication.repositories;

import com.example.shippingcompanyapplication.entities.ShippmentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageInformationRepository extends JpaRepository<ShippmentInformation, Integer> {
    ShippmentInformation save(ShippmentInformation packageInformation);
}
