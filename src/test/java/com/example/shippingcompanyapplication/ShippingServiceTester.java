package com.example.shippingcompanyapplication;

import com.example.shippingcompanyapplication.common_classes.ShippingType;
import com.example.shippingcompanyapplication.entities.PackageDetail;
import com.example.shippingcompanyapplication.entities.ShippedPackage;
import com.example.shippingcompanyapplication.entities.ShippmentInformation;
import com.example.shippingcompanyapplication.repositories.ShippingRepository;
import com.example.shippingcompanyapplication.services.ShippingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShippingServiceTester {
    @Mock
    ShippingRepository shippingRepository;
    @Mock
    PricingClient pricingClient;

    @InjectMocks
    ShippingService shippingService;

    @Test
    public void testCorrectShippingInformation_IsReturned(){
        ShippedPackage shippedPackage = ShippedPackage.builder().productName("Book")
                .productDescription("This is best selling book of 2023!").weight(50.00)
                .shippingtype(ShippingType.STANDARD).build();

        when(shippingRepository.save(shippedPackage)).thenReturn(shippedPackage);

        PackageDetail packageDetail = PackageDetail.builder().weight(shippedPackage.getWeight())
                .shippingtype(shippedPackage.getShippingtype()).build();
        when(pricingClient.getPricing(packageDetail)).thenReturn(500.00);

        double pricing = pricingClient.getPricing(packageDetail);

        ShippmentInformation expectedShippmentInformation = ShippmentInformation.builder()
                .productName(shippedPackage.productName).productDescription(shippedPackage.productDescription).build();

        ShippmentInformation actualShippmentInformation = shippingService.shipPackage(shippedPackage);
        actualShippmentInformation.setTrackingNumber(null);
        actualShippmentInformation.setShippedDate(null);

        assertEquals(expectedShippmentInformation, actualShippmentInformation);
    }
}
