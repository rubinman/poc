package com.myRetail.poc;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class PocController {
    //Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics

    final String endPoint = "https://redsky.target.com/v2/pdp/tcin/%d?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    @GetMapping("/products/{id}")
    Product getProduct(@PathVariable Long id) {
        Product product = new Product();

        Optional<String> optionalName = getProductNameFromEndPoint(id);
        if (optionalName.isPresent()) {
            product.setId(id.toString());
            product.setName(optionalName.get());
            Optional<Price> optionalPrice = getProductPriceFromEndPoint(id);
            if (optionalPrice.isPresent()) {
                product.setCurrentPrice(optionalPrice.get());
            }
        }
        return product;
    }

    private Optional<String> getProductNameFromEndPoint(Long id) {
        String productName = null;

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                HttpMethod.GET,
                URI.create(String.format(endPoint, id))
        );

        ResponseEntity<Map> result = restTemplate.exchange(
                requestEntity, Map.class);

        if (result.getStatusCode().is2xxSuccessful()) {
            productName = getProductName(result.getBody());
        }

        return Optional.of(productName);

    }

    private Optional<Price> getProductPriceFromEndPoint(Long id) {
        Price price = null;

        String endPoint = "http://localhost:8080/product/%d/price";
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                HttpMethod.GET,
                URI.create(String.format(endPoint, id))
        );

        ResponseEntity<Price> result = restTemplate.exchange(
                requestEntity, Price.class);

        if (result.getStatusCode().is2xxSuccessful()) {
            price = result.getBody();
        }
        return Optional.of(price);
    }

    private String getProductName(Map<String, Map> response) {
        String productName = null;
        try {
            Map<String, Map> product = response.get("product");
            Map<String, Map> item = product.get("item");
            Map<String, Object> product_description = item.get("product_description");
            productName = (String) product_description.get("title");
        } catch (Exception e) {

        }

        return productName;
    }

    @GetMapping("/product/{id}/price")
    ResponseEntity<Price> getProductPrice(@PathVariable Long id) {
        BigDecimal bd = new BigDecimal(13.49);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return ResponseEntity.status(HttpStatus.OK).body(new Price(bd, "USD"));
    }
}
