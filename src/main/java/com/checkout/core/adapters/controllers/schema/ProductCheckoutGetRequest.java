package com.checkout.core.adapters.controllers.schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCheckoutGetRequest {
    String name;
    Integer amount;
}
