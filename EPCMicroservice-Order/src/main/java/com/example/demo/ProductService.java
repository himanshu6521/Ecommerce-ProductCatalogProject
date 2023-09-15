package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@ComponentScan

@FeignClient(name = "ProductService")
public interface ProductService {

	//using feignClient to access product service in order service
	@GetMapping("/products/getPricebyProductId/{productId}")
    Long getProductPrice(@PathVariable("productId") Long productId);
}