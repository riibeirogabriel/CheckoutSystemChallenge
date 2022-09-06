package com.checkout.core.entities.promotion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuyXGetYFreeTest {
    @Test
    void calculate_promotionWhenProductsAmountIsNotEnough_totalValueWithoutPromotion(){
        Integer requiredQty = 3;
        Integer freeQty = 1;
        Integer productValue = 2333;
        Integer productAmount = 2;
        Integer promotionValue = 0;

        PromotionCommand promotion = new BuyXGetYFree(requiredQty, freeQty);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }

    @Test
    void calculate_promotionWhenProductsAmountIsEnough_appliedPromotionInTotalValue(){
        Integer requiredQty = 3;
        Integer freeQty = 1;
        Integer productValue = 2333;
        Integer productAmount = 4;

        PromotionCommand promotion = new BuyXGetYFree(requiredQty, freeQty);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(productValue, promotionResult);
    }
}
