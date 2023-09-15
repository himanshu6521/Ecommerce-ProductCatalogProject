package com.example.configuration;

import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InventoryConfig {

	@Bean @Primary
	public RestTemplate InventoryRestTemplate() {
		return new RestTemplate();
	}
}
