package com.checkout.core.entities.promotion;

public class QtyBasedPriceOverride extends PromotionCommand {
    Integer requiredQty;
    Integer price;

    public QtyBasedPriceOverride(Integer requiredQty, Integer price) {
        this.requiredQty = requiredQty;
        this.price = price;
    }

    @Override
    public Integer calculate(Integer productValue, Integer productAmount) {
        Integer ProductsRequiredAmount = productAmount / requiredQty;

        Integer productRequiredAmountValue = productValue * requiredQty;
        Integer promotionValueByEachRequiredAmount = productRequiredAmountValue - price;

        Integer promotionValue = ProductsRequiredAmount * promotionValueByEachRequiredAmount;

        return promotionValue;

    }
}
