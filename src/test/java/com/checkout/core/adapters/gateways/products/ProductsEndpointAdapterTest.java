package com.checkout.core.adapters.gateways.products;

import com.checkout.core.adapters.gateways.products.interfaces.external.ProductsWebAPI;
import com.checkout.core.entities.Product;
import com.checkout.core.entities.promotion.QtyBasedPriceOverride;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductsEndpointAdapterTest {

    @MockBean
    ProductsWebAPI productsAPI;

    @Autowired
    ProductsEndpointAdapter productsEndpointAdapter;

    @Test()
    void getProductInfo_whenProductIdIsInvalid_ThrewException() throws HttpClientErrorException {
        String productId = "invalidId";
        when(productsAPI.getProductInfo(productId)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        try {
            productsEndpointAdapter.getProductInfo(productId);
            fail("Exception didn't throw");
        } catch (HttpClientErrorException ignored) {
        }
    }

    @Test
    void getProductInfo_whenProductIdIsValid_ValidObject() throws HttpClientErrorException {
        String productId = "Dwt5F7KAhi";
        String productJsonContent = """
                {
                      "id": "Dwt5F7KAhi",
                      "name": "Amazing Pizza!",
                      "price": 1099,
                      "promotions": [
                        {
                          "id": "ibt3EEYczW",
                          "type": "QTY_BASED_PRICE_OVERRIDE",
                          "required_qty": 2,
                          "price": 1799
                        }
                      ]
                    }
                """;

        ResponseEntity<String> responseEntity = mock(ResponseEntity.class);

        when(responseEntity.getBody()).thenReturn(productJsonContent);
        when(productsAPI.getProductInfo(productId)).thenReturn(responseEntity);

        Product product = productsEndpointAdapter.getProductInfo(productId);

        Assertions.assertNotNull(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertNotNull(product.getName());
        Assertions.assertNotNull(product.getPrice());

        Assertions.assertEquals(1, product.getPromotions().size());
        Assertions.assertSame(product.getPromotions().get(0).getClass(), QtyBasedPriceOverride.class);
    }

}
