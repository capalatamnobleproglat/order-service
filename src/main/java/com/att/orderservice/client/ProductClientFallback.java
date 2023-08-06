package com.att.orderservice.client;

import com.att.orderservice.dto.ProductDto;
import org.springframework.stereotype.Service;

@Service
public class ProductClientFallback implements ProductClient {
    @Override
    public ProductDto getProduct(Long id) {
        ProductDto fallbackProduct = new ProductDto();
        fallbackProduct.setId(0L);
        fallbackProduct.setName("name");
        fallbackProduct.setPrice(0);

        return fallbackProduct;
    }
}
