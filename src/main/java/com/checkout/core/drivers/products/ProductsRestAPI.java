package com.checkout.core.drivers.products;

import com.checkout.core.adapters.gateways.products.interfaces.external.ProductsWebAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ProductsRestAPI implements ProductsWebAPI {

    @Value("${driver.products.rest.api.endpoint}")
    private String apiProductsEndpoint;

    public ResponseEntity<String> getProductInfo(String productId) throws HttpClientErrorException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = String.join("/", apiProductsEndpoint, productId);
        URI uri;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
            return restTemplate.getForEntity(uri, String.class);
    }
}
