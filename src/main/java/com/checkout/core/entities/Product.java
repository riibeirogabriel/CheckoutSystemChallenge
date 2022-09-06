package com.checkout.core.entities;

import com.checkout.core.entities.promotion.PromotionCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Product {
    String id;
    String name;
    Integer price;
    List<PromotionCommand> promotions;

    public Product(String id, String name, Integer price, List<PromotionCommand> promotions) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotions = promotions;
    }

    public Integer getTotalPrice(Integer quantity) {
        return price * quantity;
    }

    public Integer getTotalDiscount(Integer quantity) {
        return promotions.stream()
                .map(promotion -> promotion.calculate(price, quantity))
                .reduce(0, Integer::sum);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
