package com.checkout.core.adapters.gateways.products.interfaces.external;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public interface ProductsWebAPI {
    ResponseEntity<String> getProductInfo(String productId) throws HttpClientErrorException;
}
