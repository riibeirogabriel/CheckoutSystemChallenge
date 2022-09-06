package com.checkout.core.entities.promotion;

public abstract class PromotionCommand {
    String id;
    String type;

    public abstract Integer calculate(Integer productValue, Integer productAmount);
}
