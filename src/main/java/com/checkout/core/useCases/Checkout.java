package com.checkout.core.useCases;

import com.checkout.core.entities.Product;
import com.checkout.core.useCases.external.interfaces.ProductsResource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class Checkout {
    ProductsResource productsResource;
    Map<String, Product> productsInBasket = new HashMap<>();
    Map<String, Integer> amountOfEachProductInBasket = new HashMap<>();

    Integer priceScale = 100;
    public Double totalValue;
    public Double totalDiscount;
    public Double totalPayable;

    DecimalFormat decimalFormat2CentsDigits = new DecimalFormat("#.##");

    @Autowired
    public Checkout(ProductsResource productsResource) {
        this.productsResource = productsResource;
    }

    public void checkout() {
        totalValue = 0.;
        totalPayable = 0.;
        totalDiscount = 0.;

        for (String productId : productsInBasket.keySet()) {
            totalValue += productsInBasket.get(productId)
                    .getTotalPrice(amountOfEachProductInBasket.get(productId));

            totalDiscount += productsInBasket.get(productId)
                    .getTotalDiscount(amountOfEachProductInBasket.get(productId));
        }

        totalValue /= priceScale;
        totalDiscount /= priceScale;
        totalPayable = totalValue - totalDiscount;

        totalValue = Double.valueOf(decimalFormat2CentsDigits.format(totalValue));
        totalDiscount = Double.valueOf(decimalFormat2CentsDigits.format(totalDiscount));
        totalPayable = Double.valueOf(decimalFormat2CentsDigits.format(totalPayable));
    }

    public List<Product> getProductsInBasket() {
        return productsInBasket.values().stream().toList();
    }

    public void addProductInBasket(String productId, Integer quantity) throws HttpClientErrorException {
        if (productsInBasket.containsKey(productId)) {
            Integer updatedProductAmount = amountOfEachProductInBasket.get(productId) + quantity;
            amountOfEachProductInBasket.put(productId, updatedProductAmount);
        } else {
            productsInBasket.put(productId, productsResource.getProductInfo(productId));
            amountOfEachProductInBasket.put(productId, quantity);
        }
    }
}
