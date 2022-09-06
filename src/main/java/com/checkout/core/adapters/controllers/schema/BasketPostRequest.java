package com.checkout.core.adapters.controllers.schema;

import lombok.Getter;

@Getter
public class BasketPostRequest {
    String productId;
    Integer productAmount;
}
