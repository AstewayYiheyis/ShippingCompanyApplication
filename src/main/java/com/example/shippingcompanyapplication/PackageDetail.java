package com.example.shippingcompanyapplication;

import com.example.shippingcompanyapplication.common_classes.ShippingType;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class PackageDetail {
    public double weight;
    public ShippingType shippingtype;
}