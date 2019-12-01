package com.myRetail.poc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PocControllerTest {

    PocController pocController;

    @BeforeEach
    public void init() {
        pocController = new PocController();
    }

    @Test
    public void getProductTest() {
        Product product = pocController.getProduct(13860428L);
        Assertions.assertEquals("13860428", product.getId());
        Assertions.assertEquals("The Big Lebowski (Blu-ray)", product.getName());
        Assertions.assertEquals( "USD", product.getCurrentPrice().getCurrencyCode());
        Assertions.assertEquals("13.49", product.getCurrentPrice().getValue().toString());
    }

}
