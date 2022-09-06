package com.checkout.core.entities.promotion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FlatPercentTest {
    @Test
    void calculate_promotionWhenThereIsNotDiscountPercentage_totalValueWithoutPromotion(){
        Integer promotionPercentageAmount = 0;
        Integer productValue = 2333;
        Integer productAmount = 2;
        Integer promotionValue = 0;

        PromotionCommand promotion = new FlatPercent(promotionPercentageAmount);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }

    @Test
    void calculate_promotionWhenThereIsDiscountPercentage_appliedPromotionInTotalValue(){
        Integer promotionPercentageAmount = 20;
        Integer productValue = 2333;
        Integer productAmount = 1;
        Integer promotionValue = 466;

        PromotionCommand promotion = new FlatPercent(promotionPercentageAmount);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }
}
