package com.checkout.core.adapters.gateways.products;

import com.checkout.core.adapters.gateways.products.interfaces.external.ProductsWebAPI;
import com.checkout.core.entities.promotion.BuyXGetYFree;
import com.checkout.core.entities.promotion.FlatPercent;
import com.checkout.core.entities.promotion.PromotionCommand;
import com.checkout.core.entities.promotion.QtyBasedPriceOverride;
import com.checkout.core.useCases.external.interfaces.ProductsResource;
import com.google.gson.*;
import com.checkout.core.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsEndpointAdapter implements ProductsResource {
    ProductsWebAPI productsAPI;

    @Autowired
    public ProductsEndpointAdapter(ProductsWebAPI productsAPI) {
        this.productsAPI = productsAPI;
    }

    @Override
    public Product getProductInfo(String productId) throws HttpClientErrorException {
        String productInfo = productsAPI.getProductInfo(productId).getBody();
        Gson gson = new Gson();
        JsonObject productObject = gson.fromJson(productInfo, JsonObject.class);
        JsonElement productPromotionsArray = productObject.get("promotions");

        List<PromotionCommand> productPromotionsList = new ArrayList<>();

        for (JsonElement promotion : productPromotionsArray.getAsJsonArray()) {
            JsonObject promotionObject = promotion.getAsJsonObject();
            String promotionType = promotionObject.get("type").getAsString();
            productPromotionsList.add(
                    promotionObjectFactory(promotionObject.toString(), promotionType));
        }

        Product product = buildProduct(productInfo);
        product.setPromotions(productPromotionsList);
        return product;
    }

    private Product buildProduct(String productJson){
        Gson gson = new Gson();
        JsonObject productObject = gson.fromJson(productJson, JsonObject.class);
        productObject.remove("promotions");
        return gson.fromJson(productObject.toString(), Product.class);
    }
    private PromotionCommand promotionObjectFactory(String promotionJson, String promotionType) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        return switch (promotionType) {
            case "BUY_X_GET_Y_FREE" -> gson.fromJson(promotionJson, BuyXGetYFree.class);
            case "QTY_BASED_PRICE_OVERRIDE" -> gson.fromJson(promotionJson, QtyBasedPriceOverride.class);
            case "FLAT_PERCENT" -> gson.fromJson(promotionJson, FlatPercent.class);
            default -> null;
        };
    }
}
