package com.checkout.core.entities.promotion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QtyBasedPriceOverrideTest {
    @Test
    void calculate_promotionWhenProductsAmountIsNotEnough_totalValueWithoutPromotion(){
        Integer requiredQty = 3;
        Integer promotionPrice = 6000;
        Integer productValue = 2333;
        Integer productAmount = 2;
        Integer promotionValue = 0;

        PromotionCommand promotion = new QtyBasedPriceOverride(requiredQty, promotionPrice);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }

    @Test
    void calculate_promotionWhenProductsAmountIsEnough_appliedPromotionInTotalValue(){
        Integer requiredQty = 3;
        Integer promotionPrice = 6000;
        Integer productValue = 2333;
        Integer productAmount = 4;
        Integer promotionValue = 999;

        PromotionCommand promotion = new QtyBasedPriceOverride(requiredQty, promotionPrice);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }

    @Test
    void calculate_promotionWhenProductsAmountIsTwoTimesMoreThanEnough_appliedPromotionInTotalValue(){
        Integer requiredQty = 3;
        Integer promotionPrice = 6000;
        Integer productValue = 2333;
        Integer productAmount = 7;
        Integer promotionValue = 1998;

        PromotionCommand promotion = new QtyBasedPriceOverride(requiredQty, promotionPrice);

        Integer promotionResult = promotion.calculate(productValue, productAmount);

        Assertions.assertEquals(promotionValue, promotionResult);
    }
}
