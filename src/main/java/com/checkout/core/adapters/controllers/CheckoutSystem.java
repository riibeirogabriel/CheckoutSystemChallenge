package com.checkout.core.adapters.controllers;

import com.checkout.core.adapters.controllers.schema.BasketPostRequest;
import com.checkout.core.adapters.controllers.schema.CheckoutGetRequest;
import com.checkout.core.adapters.controllers.schema.GenericResponseRequest;
import com.checkout.core.adapters.controllers.schema.ProductCheckoutGetRequest;
import com.checkout.core.entities.Product;
import com.checkout.core.useCases.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
public class CheckoutSystem {
    Checkout checkout;

    @Autowired
    public CheckoutSystem(Checkout checkout) {
        this.checkout = checkout;
    }

    @PostMapping(path = "/basket", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponseRequest addProductInBasket(@RequestBody BasketPostRequest body) throws HttpClientErrorException {
        checkout.addProductInBasket(body.getProductId(), body.getProductAmount());

        GenericResponseRequest genericResponseRequest = new GenericResponseRequest();
        genericResponseRequest.setMessage("Product added in basket");
        return genericResponseRequest;
    }

    @GetMapping(path = "/checkout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckoutGetRequest getCheckoutOfItemsInBasket() {
        checkout.checkout();
        List<Product> productsInCheckout = checkout.getProductsInBasket();
        Double totalValue = checkout.getTotalValue();
        Double totalDiscount = checkout.getTotalDiscount();

        CheckoutGetRequest checkoutGetRequest = new CheckoutGetRequest();
        for (Product product : productsInCheckout) {
            ProductCheckoutGetRequest productCheckoutGetRequest = new ProductCheckoutGetRequest();
            productCheckoutGetRequest.setName(product.getName());
            productCheckoutGetRequest.setAmount(
                    checkout.getAmountOfEachProductInBasket().get(product.getId()));

            checkoutGetRequest.getBasketContents().add(productCheckoutGetRequest);
        }

        checkoutGetRequest.setRawTotal(totalValue);
        checkoutGetRequest.setTotalPromos(totalDiscount);
        checkoutGetRequest.setTotalPayable(checkout.getTotalPayable());

        return checkoutGetRequest;
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericResponseRequest> exceptionHappened(HttpClientErrorException exception) {
        GenericResponseRequest genericResponseRequest = new GenericResponseRequest();

        switch (exception.getStatusCode()) {
            case NOT_FOUND -> {
                genericResponseRequest.setMessage("Product not found");
                return new ResponseEntity<>(genericResponseRequest, HttpStatus.NOT_FOUND);
            }
            case INTERNAL_SERVER_ERROR -> {
                genericResponseRequest.setMessage("System unavailable, please try again later");
                return new ResponseEntity<>(genericResponseRequest, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            default -> {
                genericResponseRequest.setMessage(exception.getMessage());
                return new ResponseEntity<>(genericResponseRequest, exception.getStatusCode());
            }
        }
    }
}
