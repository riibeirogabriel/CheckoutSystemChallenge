package com.checkout.core.adapters.controllers.schema;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CheckoutGetRequest {
    List<ProductCheckoutGetRequest> basketContents;
    Double rawTotal;
    Double totalPromos;
    Double totalPayable;

    public CheckoutGetRequest() {
        this.basketContents = new ArrayList<>();
    }
}

