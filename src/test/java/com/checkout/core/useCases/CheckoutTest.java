package com.checkout.core.useCases;

import com.checkout.core.entities.Product;
import com.checkout.core.useCases.external.interfaces.ProductsResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DecimalFormat;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CheckoutTest {

    @MockBean
    ProductsResource productsResource;

    @Test
    void addProductInBasket_simpleProductAddition_productAddedInBasket() throws HttpClientErrorException {
        Integer productAmount = 2;
        String productId = "Dwt5F7KAhi";

        Product product = mock(Product.class);
        when(product.getId()).thenReturn(productId);

        when(productsResource.getProductInfo(productId)).thenReturn(product);
        Checkout checkout = new Checkout(productsResource);
        checkout.addProductInBasket(productId, productAmount);

        List<Product> addedProducts = checkout.getProductsInBasket();

        Assertions.assertNotNull(addedProducts);
        Assertions.assertEquals(addedProducts.get(0).getId(), productId);
    }

    @Test
    void checkout_checkoutAfterAlreadyAddedSomeProducts_correctCheckoutValues() throws HttpClientErrorException {
        DecimalFormat decimalFormat2CentsDigits = new DecimalFormat("#.##");
        Integer priceScale = 100;

        Integer firstProductAmount = 2;
        Integer firstProductTotalValue = 1800;
        Integer firstProductTotalDiscountValue = 300;
        String firstProductId = "Dwt5F7KAhi";

        Integer secondProductAmount = 3;
        Integer secondProductTotalValue = 800;
        Integer secondProductTotalDiscountValue = 100;
        String secondProductId = "C8GDyLrHJb";

        Double totalValue = Double.valueOf(decimalFormat2CentsDigits.format(
                (firstProductTotalValue + secondProductTotalValue) / priceScale));
        Double totalDiscountValue = Double.valueOf(decimalFormat2CentsDigits.format(
                (firstProductTotalDiscountValue + secondProductTotalDiscountValue) / priceScale));
        Double totalPayableValue = totalValue - totalDiscountValue;

        Product firstProduct = mock(Product.class);
        when(firstProduct.getId()).thenReturn(firstProductId);
        when(firstProduct.getTotalPrice(firstProductAmount)).thenReturn(firstProductTotalValue);
        when(firstProduct.getTotalDiscount(firstProductAmount)).thenReturn(firstProductTotalDiscountValue);

        Product secondProduct = mock(Product.class);
        when(secondProduct.getId()).thenReturn(secondProductId);
        when(secondProduct.getTotalPrice(secondProductAmount)).thenReturn(secondProductTotalValue);
        when(secondProduct.getTotalDiscount(secondProductAmount)).thenReturn(secondProductTotalDiscountValue);

        when(productsResource.getProductInfo(firstProductId)).thenReturn(firstProduct);
        when(productsResource.getProductInfo(secondProductId)).thenReturn(secondProduct);

        Checkout checkout = new Checkout(productsResource);
        checkout.addProductInBasket(firstProductId, firstProductAmount);
        checkout.addProductInBasket(secondProductId, secondProductAmount);

        checkout.checkout();

        Double totalValueResult = checkout.getTotalValue();
        Double totalDiscountResult = checkout.getTotalDiscount();
        Double totalPayableValueResult = checkout.getTotalPayable();

        Assertions.assertNotNull(checkout.getProductsInBasket());
        Assertions.assertEquals(totalValue, totalValueResult);
        Assertions.assertEquals(totalDiscountValue, totalDiscountResult);
        Assertions.assertEquals(totalPayableValue, totalPayableValueResult);
    }
}
