package com.example.shippingcompanyapplication;

import com.example.shippingcompanyapplication.entities.PackageDetail;
import com.example.shippingcompanyapplication.entities.PricingInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

@Component
public class PricingClient {
    RestTemplate restTemplate;

    public PricingClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    public double getPricing(PackageDetail packageDetail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI url = new URI("http://localhost:8081/shipping/pricing");
        HttpEntity<PackageDetail> entity = new HttpEntity<>(packageDetail);

        ResponseEntity<PricingInfo> response = restTemplate.postForEntity(url, entity, PricingInfo.class);

        double price = response.getBody().getPrice();

        return price;
    }
}
