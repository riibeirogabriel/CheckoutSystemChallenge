package com.checkout.core.useCases.external.interfaces;

import com.checkout.core.entities.Product;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

public interface ProductsResource {
    Product getProductInfo(String productId) throws HttpClientErrorException;
}
