package com.checkout.core.entities.promotion;

public class FlatPercent extends PromotionCommand {
    Integer amount;

    public FlatPercent(Integer amount) {
        this.amount = amount;
    }

    @Override
    public Integer calculate(Integer productValue, Integer productAmount) {

        Integer totalProductsValue = productValue * productAmount;
        Double percentageValue = Double.valueOf(amount) / 100;
        Double promotionValue = totalProductsValue * percentageValue;
        return promotionValue.intValue();
    }
}
