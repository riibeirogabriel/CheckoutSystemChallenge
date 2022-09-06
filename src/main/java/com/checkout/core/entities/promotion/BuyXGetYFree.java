package com.checkout.core.entities.promotion;

public class BuyXGetYFree extends PromotionCommand {
    Integer requiredQty;
    Integer freeQty;

    public BuyXGetYFree(Integer requiredQty, Integer freeQty) {
        this.requiredQty = requiredQty;
        this.freeQty = freeQty;
    }

    @Override
    public Integer calculate(Integer productValue, Integer productAmount) {
        Integer ProductsRequiredAmount = productAmount / requiredQty;

        Integer freeProductsAmount = ProductsRequiredAmount * freeQty;
        Integer promotionValue = freeProductsAmount * productValue;

        return promotionValue;
    }
}
