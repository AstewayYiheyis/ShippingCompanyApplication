package com.example.shippingcompanyapplication.repositories;

import com.example.shippingcompanyapplication.entities.PackageInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageInformationRepository extends JpaRepository<PackageInformation, Integer> {
    PackageInformation save(PackageInformation packageInformation);
}
