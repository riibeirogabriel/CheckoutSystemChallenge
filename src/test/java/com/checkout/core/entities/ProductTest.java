package com.checkout.core.entities;

import com.checkout.core.entities.promotion.BuyXGetYFree;
import com.checkout.core.entities.promotion.PromotionCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductTest {
    @Test
    void getTotalPrice_totalPriceOfMultipleProducts_correctPriceWithMultipleProducts(){

        Integer productValue = 1333;
        Integer productAmount = 2;
        Integer totalValue = productValue * productAmount;
        List<PromotionCommand> promotionCommands = new ArrayList<>();

        Product product = new Product(
                "someId", "productName", productValue, promotionCommands);

        Integer valueResult = product.getTotalPrice(productAmount);

        Assertions.assertEquals(valueResult, totalValue);
    }

    @Test
    void getTotalPrice_totalDiscountOfMultipleProducts_correctPriceWithAppliedDiscount(){

        Integer productValue = 1333;
        Integer productAmount = 2;
        Integer promotionValue = 666;
        PromotionCommand promotion = mock(BuyXGetYFree.class);

        when(promotion.calculate(productValue, productAmount)).thenReturn(promotionValue);
        List<PromotionCommand> promotionCommands = new ArrayList<>();
        promotionCommands.add(promotion);

        Product product = new Product(
                "someId", "productName", productValue, promotionCommands);

        Integer valueResult = product.getTotalDiscount(productAmount);

        Assertions.assertEquals(valueResult, promotionValue);
    }
}
